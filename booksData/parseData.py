import json

computerBooks = json.load(open('computer.json', 'r'))
healthBooks = json.load(open('health.json', 'r'))
sportBooks = json.load(open('sport.json', 'r'))
historyBooks = json.load(open('history.json', 'r'))
novelBooks = json.load(open('novel.json', 'r'))
cookBooks = json.load(open('cook.json', 'r'))

# csv format: isbn10, isbn13, title, author, publisher, publish date, page count, image link, category
csvString = []

allBooks = {
    "Computer": computerBooks, 
    "Health": healthBooks, 
    "Sport": sportBooks, 
    "History": historyBooks,
    "Novel": novelBooks,
    "Cook": cookBooks
}

for category, books in allBooks.items():

    for bookItem in books['items']:

        try:
            book = bookItem['volumeInfo']

            industryIdentifiers = book['industryIdentifiers']        
            for iid in industryIdentifiers:
                if iid['type'] == 'ISBN_10':
                    isbn10 = "'" + iid['identifier'] + "'"
                elif iid['type']  == 'ISBN_13':
                    isbn13 = "'" + iid['identifier'] + "'"

            title = book['title'].replace(",", "")
            if book['authors']:
                author = book['authors'][0].replace(",", "")
            else:
                author = []
            publisher = book['publisher'].replace(",", "")
            publishedDate = book['publishedDate'].replace(",", "")
            pageCount = str(book['pageCount']).replace(",", "")

            if book['imageLinks']:
                imageLink = book['imageLinks']['thumbnail']

            csvString.append(",".join([isbn10, isbn13, title, author, publisher, publishedDate, pageCount, imageLink, category]))

        except KeyError:
            continue

print("\n".join(csvString))