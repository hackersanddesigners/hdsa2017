import sys
import time

while True:
  c = sys.stdin.read(1)
  if c == "?":
    print("Asked a question.")
  elif c == "a":
    print("The letter a.")
  else:
    print("Something else.")
  time.sleep(1)

