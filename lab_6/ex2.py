num = [2, 4, 6, 8]

it1 = iter(num)
it2 = iter(map(lambda x: x ** 4, num))

print(next(it2))
print(next(it1))

num[2] = 0
num[3] = "Hello"

print(next(it2))
print(next(it2))