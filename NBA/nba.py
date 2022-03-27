from requests import get
from pprint import PrettyPrinter

BASE_URL = "https://data.nba.net"
ALL_JSON = "/prod/v1/today.json"

printer = PrettyPrinter()

def get_links():
    data = get(BASE_URL + ALL_JSON).json()
    links = data['links']
    return links


def get_scoreboard():
    scoreboard = get_links()['currentScoreboard']
    games = get(BASE_URL + scoreboard).json()['games']

    for game in games:
        home_team = game['hTeam']
        away_team = game['vTeam']
        clock = game['clock']
        period = game['period']

        print("---------------------------------------------------------------")
        print(f"{home_team['triCode']} vs {away_team['triCode']}")
        print(f"{home_team['score']} vs {away_team['score']}")
        print(f"{clock} - {period['current']} quarter")


def get_regular_season_ppg():
    stats = get_links()['leagueTeamStatsLeaders']
    teams = get(BASE_URL + stats).json()['league']['standard']['regularSeason']['teams']

    teams = list(filter(lambda x: x['name'] != "Team", teams))
    teams.sort(key = lambda x: int(x['ppg']['rank']))
    for i, team in enumerate(teams):
        name = team['name']
        nickname = team['nickname']
        ppg = team['ppg']['avg']
        print(f"{i + 1}.  {name} {nickname} averaging {ppg}. points per game")


def west_confrence_standings():
    table = get_links()['leagueConfStandings']
    teams = get(BASE_URL + table).json()['league']['standard']['conference']['west']

    for team in teams:
        city = team['teamSitesOnly']['teamName']
        nickname = team['teamSitesOnly']['teamNickname']
        name = city + " " + nickname
        rank = team['confRank']
        wins = team['win']
        loss = team['loss']
        win_percentage = team['winPct']
        print(f"{rank}. {name} {wins}-{loss} winning percentage = 0{win_percentage}")


def east_confrence_standings():
    table = get_links()['leagueConfStandings']
    teams = get(BASE_URL + table).json()['league']['standard']['conference']['east']

    for team in teams:
        city = team['teamSitesOnly']['teamName']
        nickname = team['teamSitesOnly']['teamNickname']
        name = city + " " + nickname
        rank = team['confRank']
        wins = team['win']
        loss = team['loss']
        win_percentage = team['winPct']
        print(f"{rank}. {name} {wins}-{loss} winning percentage = 0{win_percentage}")


def main():
    print("Welcome to the NBA app")
    print(" Press (1) to get today's scoreboard")
    print(" Press (2) to get the west conference standings")
    print(" Press (3) to get the east conference standings")
    print(" Press (4) to get teams points per game")
    print(" Press q to quit the app")
    print(" Press h for help \n")
    print("---------------------------------------------------------------")

    while True:
        command = input("Pick an option : ").lower()

        if command == "q":
            break
        elif command == "h":
            print(" Press (1) to get today's scoreboard")
            print(" Press (2) to get the west conference standings")
            print(" Press (3) to get the east conference standings")
            print(" Press (4) to get teams points per game")
            print(" Press q to quit the app")
            print(" Press h for help \n")
        elif command == "1":
            get_scoreboard()
            print("---------------------------------------------------------------")
            print()
        elif command == "2":
            west_confrence_standings()
            print("---------------------------------------------------------------")
            print()
        elif command == "3":
            east_confrence_standings()
            print("---------------------------------------------------------------")
            print()
        elif command == "4":
            get_regular_season_ppg()
            print("---------------------------------------------------------------")
            print()
        else:
            print("Unrecognized command! \n")



main()
