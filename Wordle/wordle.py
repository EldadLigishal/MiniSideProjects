import random
import sys
from valid_words import valid_words

CHOSEN_WORD = random.choice(valid_words)
GUESS_CNT = 6

class Color:
    PREFIX = '\033'
    BASE = "\033[0m"
    GREY = "\033[90m"
    RED = "\033[91m"
    GREEN = "\033[92m"
    YELLOW = "\033[93m"
    PERSISTENT_COLORS = [RED, GREEN]



class GuessWord:
    cnt = 1
    words = []
    alphabet = {
        "a": "a",
        "b": "b",
        "c": "c",
        "d": "d",
        "e": "e",
        "f": "f",
        "g": "g",
        "h": "h",
        "i": "i",
        "j": "j",
        "k": "k",
        "l": "l",
        "m": "m",
        "n": "n",
        "o": "o",
        "p": "p",
        "q": "q",
        "r": "r",
        "s": "s",
        "t": "t",
        "u": "u",
        "v": "v",
        "w": "w",
        "x": "x",
        "y": "y",
        "z": "z",
    }
    def __init__(self, word_str:str):
        self.w_str = word_str
        self.w_chars = list(self.w_str)
        self.post_guess_w_str = ""

    def jump_turn(self):
        GuessWord.cnt += 1


    def is_valid_word(self):
        return self.w_str in valid_words

    def apply_green(self):
        for i, _ in enumerate(self.w_chars):
            actual_char = CHOSEN_WORD[i]
            guessed_char = self.w_chars[i]
            if actual_char == guessed_char:
                colored_char = f"{Color.GREEN}{actual_char}{Color.BASE}"
                self.w_chars[i] = colored_char
                self.edit_alphabet(actual_char, colored_char)


    def apply_yellow(self):
        for i, _ in enumerate(self.w_chars):
            guessed_char = self.w_chars[i]
            if guessed_char in CHOSEN_WORD:
                colored_char = f"{Color.YELLOW}{guessed_char}{Color.BASE}"
                self.w_chars[i] = colored_char
                self.edit_alphabet(guessed_char,colored_char)
            else:
                colored_char = f"{Color.RED}{guessed_char}{Color.BASE}"
                self.edit_alphabet(guessed_char, colored_char)


    def edit_alphabet(self, k , v):
        if k not in GuessWord.alphabet.keys():
            return

        older_val = GuessWord.alphabet.get(k, "")
        modify_color = True
        for c in Color.PERSISTENT_COLORS:
            if c in older_val:
                modify_color = False
        GuessWord.alphabet[k] = v


    def apply_guesses(self):
        self.apply_green()
        self.apply_yellow()
        self.post_guess_w_str = "".join(self.w_chars)
        GuessWord.words.append(self.post_guess_w_str)
        print(self.post_guess_w_str)


    def check_if_won(self):
        if self.w_str == CHOSEN_WORD:
            print(f"Congrats! You beat wordle in {GuessWord.cnt} guesses")
            for word in GuessWord.words:
                print(word)
            sys.exit(1)


    def check_if_lost(self):
        if GuessWord.cnt == GUESS_CNT + 1:
            print(f"No guesses left, You lost the game :( . The word was {CHOSEN_WORD}")
