import csv


class Subscriber:

    def _normalize(self, s):
        return s.strip().replace("\t","").replace("\n","")

    def __init__(self, id, firstname, surname, title, address, town, country, postcode, subscription_paid, gender, date_of_birth):
        self.id = self._normalize(id)
        self.firstname = self._normalize(firstname)
        self.surname = self._normalize(surname)
        self.title = self._normalize(title)
        self.address = self._normalize(address)
        self.town = self._normalize(town)
        self.country = self._normalize(country)
        self.postcode = self._normalize(postcode)
        self.subscription_paid = self._normalize(subscription_paid)
        self.gender = self._normalize(gender)
        self.date_of_birth = self._normalize(date_of_birth)


def loadDatabase():
    subscribers = []
    with open('people.csv') as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                # print(f'Column names are {", ".join(row)}')
                line_count += 1
            else:
                subscribers.append(Subscriber(
                    row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10]
                ))
                line_count += 1
        # print(f'Processed {line_count} lines.')
    return subscribers

def PaymentFromGB(sub):
    return list(filter(lambda x: x.country == "gb" and x.subscription_paid == "true", sub))

print("PaymentFromGB => " + str(len(PaymentFromGB(loadDatabase()))))
