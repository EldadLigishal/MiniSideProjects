#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <signal.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

#define BUFSIZE 10

void error(char* msg);
int process_arglist(int count, char **arglist);
int background_process(int count, char **arglist);
int input_redirection(int count, char **arglist);
int output_redirection(int count, char **arglist);
int run(int count, char **arglist);
int piped_process(int index, int count, char **arglist);
int prepare(void);
void mySignalHandler(int pid);
int finalize(void);
void terminate_sigint();

// how to use execvp:
//      char* arr[] = {"filename", "command1", "command2"};
//      execvp(filename, arr);
int process_arglist(int count, char **arglist) {
    int i;
    // Executing commands in the background
    if (strcmp(arglist[count - 1], "&") == 0) {
        return background_process(count - 1, arglist);
    }

    // look for the other operators
    for(i = 0; i < count - 1; i++) {
        // Piping
        if (strcmp(arglist[i], "|") == 0) {
            return piped_process(i, count, arglist);
        }

        // Input redirection
        else if (strcmp(arglist[i], "<") == 0) {
            return input_redirection(count, arglist);
        }

        // Output redirection
        else if (strcmp(arglist[i], ">") == 0) {
            return output_redirection(count, arglist);
        }
    }

    return run(count, arglist);
}

// example: cat < filename.txt
int input_redirection(int count, char **arglist) {
    arglist[count - 2] = NULL;

    int pid = fork();
    if (pid == -1) {
        error("Can't execute fork() method properly");
        return 0;
    }
    else if (pid == 0) {
        // terminate upon SIGINT
        terminate_sigint();

        // open the specified file with read only permission
        int file = open(arglist[count - 1], O_RDONLY);
        if (file == -1) {
            exit(1); // TODO: check if needs to be changed
        }
        if (dup2(file, STDIN_FILENO) == -1) {
            error("dup2 failed");
            exit(1);
        }
        close(file);
        // execute
        if (execvp(arglist[0], arglist) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }

    // wait for process to complete
    if (waitpid(pid, NULL, 0) == -1 && errno != ECHILD && errno != EINTR) {
        error("waitpid returned process didn't finish");
        return 0;
    }
    return 1;
}

// example: cat foo > filename.txt
int output_redirection(int count, char **arglist) {
    arglist[count - 2] = NULL;
    int pid = fork();
    if (pid == -1) {
        error("Can't execute fork() method properly");
        return 0;
    }
    else if (pid == 0) {
        // terminate upon SIGINT
        terminate_sigint();

        // open the specified file
        int file = open(arglist[count - 1], O_WRONLY | O_CREAT | O_TRUNC, 0666);
        if (file == -1) {
            exit(1); // TODO: check if needs to be changed
        }
        if (dup2(file, STDOUT_FILENO) == -1) {
            error("dup2 failed");
            exit(1);
        }
        close(file);
        // execute
        if (execvp(arglist[0], arglist) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }

    // wait for process to complete
    if (waitpid(pid, NULL, 0) == -1 && errno != ECHILD && errno != EINTR) {
        error("waitpid returned process didn't finish");
        return 0;
    }
    return 1;
}

void terminate_sigint() {
    struct sigaction sig;
    sig.sa_handler = SIG_DFL;
    if (sigaction(SIGINT, &sig, NULL) == -1) {
        error("Can't execute sigaction!");
        exit(1);
    }   
}

int run(int count, char **arglist){
    int pid = fork();
    if (pid == -1) {
        error("Can't execute fork() method properly");
        return 0;
    }

    else if (pid == 0) {
        // terminate upon SIGINT
        terminate_sigint();
        
        // execute
        if (execvp(arglist[0], arglist) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }

    // wait for process to complete
    if (waitpid(pid, NULL, 0) == -1 && errno != ECHILD && errno != EINTR) {
        error("waitpid returned process didn't finish");
        return 0;
    }
    return 1;
}

int piped_process(int index, int count, char **arglist){
    arglist[index] = NULL;
    int pfds[2];
    if (pipe(pfds) == -1) {
        error("Pipe() method failed");
        return 1;
    }
    int pid1 = fork();
    if (pid1 == - 1) {
        error("Can't execute fork() method properly");
        return 0;
    }
    if (pid1 == 0) {
        terminate_sigint();
        // redirect the output to the pipe
        if(dup2(pfds[1], STDOUT_FILENO) == - 1) {
            error("dup2 failed");
            exit(1);
        }
        close(pfds[0]);
        close(pfds[1]);
        
        // execute
        if (execvp(arglist[0], arglist) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }

    int pid2 = fork();
    if (pid2 == - 1) {
        error("Can't execute fork() method properly");
        return 0;
    }
    if (pid2 == 0) {
        terminate_sigint();
        // redirect the pipe to input
        if(dup2(pfds[0], STDIN_FILENO) == - 1) {
            error("dup2 failed");
            exit(1);
        }

        // close read&write
        close(pfds[0]);
        close(pfds[1]);
        
        // execute
        if (execvp(arglist[index + 1], &arglist[index + 1]) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }
    
    close(pfds[0]);
    close(pfds[1]);

    // wait for child 1 to complete
    if (waitpid(pid1, NULL, 0) == -1 && errno != ECHILD && errno != EINTR) {
        error("waitpid returned process didn't finish");
        return 0;
    }

    // wait for child 2 to complete
    if (waitpid(pid2, NULL, 0) == -1 && errno != ECHILD && errno != EINTR) {
        error("waitpid returned process didn't finish");
        return 0;
    }

    return 1;
}

int background_process(int count, char **arglist) {
    // Do not pass the & argument
    arglist[count] = NULL;

    int pid = fork();

    if (pid < 0) {
        error("Can't execute fork() method properly");
        return 0;
    }
    
    if (pid == 0) {
        if (execvp(arglist[0], arglist) == -1) {
            error("Can't execute execvp() method properly");
            exit(1);
        }
    }
    return 1;
}

void mySignalHandler(int pid) {
    int cnt;
    // waitnig for any child procces
    while((cnt = waitpid(-1, NULL, WNOHANG)) > 0) {
        continue;
    }
    if (cnt == -1 && errno != ECHILD) {
        error("waitpid returned process didn't finish");
        exit(1);
    }
}

int prepare(void) {
    // Ignore SIGIGN
    struct sigaction ctrl;
    ctrl.sa_handler = SIG_IGN;
    ctrl.sa_flags = SA_RESTART;
    if (sigaction(SIGINT, &ctrl, NULL) == -1) {
        error("Can't execute signaction!");
        return 1;
    }

    // Ignore ECHILD
    struct sigaction sig;
    sig.sa_handler = &mySignalHandler;
    sig.sa_flags = SA_RESTART;
    if (sigaction(SIGCHLD, &sig, NULL) == -1) {
        error("Can't execute signaction!");
        return 1;
    }
    
    return 0;
}


int finalize(void) {
    return 0;
}



void error(char* msg) {
	fprintf(stderr, "Error: %s\n", msg);
}
