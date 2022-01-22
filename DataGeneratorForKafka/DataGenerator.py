from datetime import datetime, timedelta
from time import sleep
import yfinance as yf

df = yf.download('BTC-USD', start=datetime.now() - timedelta(days=7), end=datetime.now(), interval='1m')

# print(df)

for index, row in df.reset_index().iterrows():
    print(row.to_json())
    sleep(2)