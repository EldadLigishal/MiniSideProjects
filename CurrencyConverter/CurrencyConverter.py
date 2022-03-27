from requests import get
from pprint import PrettyPrinter

baseURL = "https://free.currconv.com/"
apiKey = "5c997ce57587cf83e0c9"

printer = PrettyPrinter()

def get_currencies():
    endpoint = f"api/v7/currencies?apiKey={apiKey}"
    url = baseURL + endpoint
    data = get(url).json()['results']

    data = list(data.items())
    data.sort()

    return data

def print_currencies(currencies):
    for name, currency in currencies:
        name = currency['currencyName']
        _id = currency['id']
        symbol = currency.get("currencySymbol", "")
        print(f"{_id} - {name} - {symbol}")


def exchange_rate(currency1, currency2):
    endpoint = f"api/v7/convert?q={currency1}_{currency2}&compact=ultra&apiKey={apiKey}"
    url = baseURL + endpoint
    response = get(url)
    data = response.json()
    if len(data) == 0:
        print("Invalid input")
        return

    rate = list(data.values())[0]
    print(f"{currency1} -> {currency2} = {rate}")
    
    return rate


def convert(currency1, currency2, amount):
    rate = exchange_rate(currency1, currency2)
    if rate is None:
        return
    try:
        amount = float(amount)
    except:
        print("Invalid amount")
        return
    
    converted_amount = rate * amount
    print(f"{amount} {currency1} is equal to {converted_amount} {currency2}")


def main():
    print("Welcome to the currency converter app")
    print("List - lists the diffrent currncies")
    print("Convert - convert from one currency to another")
    print("Rate - get the exchange rate of two currencies \n")

    while True:
        currencies = get_currencies()
        command = input("Pick an option (press q to quit or h for help): ").lower()

        if command == "q":
            break
        elif command == "h":
            print("List - lists the diffrent currncies")
            print("Convert - convert from one currency to another")
            print("Rate - get the exchange rate of two currencies \n")
        elif command == "list":
            print_currencies(currencies)
            print()
        elif command == "convert":
            c1 = input("Enter a base currency: ").upper()
            amount = input(f"Enter the amount in {c1}: ")
            c2 = input("Enter a currency to convert to: ").upper()
            convert(c1, c2, amount)
            print()
        elif command == "rate":
            c1 = input("Enter the first currency: ").upper()
            c2 = input("Enter the second currency to convert to: ").upper()
            exchange_rate(c1, c2)
            print()
        else:
            print("Unrecognized command! \n")


main()