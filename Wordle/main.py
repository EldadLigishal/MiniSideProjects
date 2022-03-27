import wordle
import os

os.system("cls") if os.name == "nt" else os.system("clear")

begin_message = """
'##:::::'##::'#######::'########::'########::'##:::::::'########:
 ##:'##: ##:'##.... ##: ##.... ##: ##.... ##: ##::::::: ##.....::
 ##: ##: ##: ##:::: ##: ##:::: ##: ##:::: ##: ##::::::: ##:::::::
 ##: ##: ##: ##:::: ##: ########:: ##:::: ##: ##::::::: ######:::
 ##: ##: ##: ##:::: ##: ##.. ##::: ##:::: ##: ##::::::: ##...::::
 ##: ##: ##: ##:::: ##: ##::. ##:: ##:::: ##: ##::::::: ##:::::::
. ###. ###::. #######:: ##:::. ##: ########:: ########: ########:
:...::...::::.......:::..:::::..::........:::........::........::
"""
print(begin_message.replace("#", f"{wordle.Color.YELLOW}#{wordle.Color.BASE}"))


if __name__ == "__main__":
    print("Welcome to the Wordle app. Here you can play Wordle all day long!\n")
    print("Press h to see the alphabet\n")
    while True:
        guess = wordle.GuessWord(word_str= input(f"[{wordle.GuessWord.cnt}]>"))
        if guess.w_str == "h":
            lst = list(wordle.GuessWord.alphabet.values())
            for element in lst:
                print(element, end=" " if lst[-1] != element else "\n")
            continue
        if guess.is_valid_word():
            guess.apply_guesses()
            guess.check_if_won()
            guess.jump_turn()
            guess.check_if_lost()

