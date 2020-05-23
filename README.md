# intro

This project aims to generate FixMessage content guided by arguments supplied through a terminal interaction.

---

# special value arguments

In order to allow random values to be generated and returned, special values can be provided.  
It is basically formed by a semicolon, followed by an identifier, a semicolon and an argument might be provided.

| argument | meaning | argument/variable |
| --- | --- | --- |
| ***:randstr:5*** | a random string of size 5 | the value 5 at the end means a variable/argument that must be provided |
| ***:today:YYYYMMdd*** | a string with a local date formatted, for example; 20200531 | the YYYYMMdd at the end means the pattern to format the today, it is an argument variable and might be provided as needed, once it is a valid ***date pattern*** |
| ***:now:YYYYMMdd-HH:mm:ss:SSS*** | a string with a local date time formatted, for example; 20200511-14:18:17:233 | the YYYYMMdd-HH:mm:ss:SSS at the end means the pattern to format the local date time for now(), it is an argument variable and might be provided as needed, once it is a valid ***date time pattern*** |
| ***:randbigdec:2*** | a positive big decimal scaled by 2 decimal digits | the value 2 at the end means a variable/argument that must be provided |
| ***:randint:*** | a positive integer value | no argument might be provided for this |
| ***:randopt:VAL_A,VAL_B*** | random options, it is retrieved only one of the available list | arguments must be provided, where options must be split by a comma |

---

# argument `-defaults` (optional)

In case the argument `-defaults` is provided, it must point to some valid file which declares the key value pair,
it follows the same rule for every implementation `-i`, for example, let's assume there's a file called my defaults with the following content;  

    -11=123
    -17=321
    -54=1

> the items within the file content addressed by the `-defaults` argument, are first loaded so later are loaded the arguments provided on the command line (terminal),
> that means values from the file content are overwritten by the arguments provided alongside the command line.

---

# argument `-i` (mandatory)

Argument `-i` is meant for the desired implementation for the generated fix message content.  

---

# drop copy created captured, fix version 4.4

    $ fixmsgen -i "fix-4.4-dc-created"
        -defaults [file address with defaul key pair values]
        -11                     <ClientOrderId> [default; random string, i.e; ":randstr:38"]
        -17                     <ExchangeExecutionID> [default; randon string, i.e; ":randstr:32"]
        -37                     <OrderID> [default; randon string, i.e; ":randstr:26"]
        -54                     <Side> [default; randon string, i.e; ":randopt:1,2"]
        -59                     <OrderValidityType> [default; randon string, i.e; ":randopt:0,3"]
        -75                     <TradeDate> [default; today as local date, i.e;":today:YYYYMMdd"]
        -60                     <TransactionTime> [default; now as local date time, i.e;":now:YYYYMMdd-HH:mm:ss:SSS"]
        -198                    <SecondaryOrderId> [default; randon string, i.e; ":randstr:26"]
        -1180                   <SessionID> [default; randon string, i.e; ":randstr:12"]
        -6032                   <TradeNumber> [default; randon string, i.e; ":randstr:10"]
        -453[n].452=7.448       <BrokerDealerCode> [default; randon string, i.e; ":randstr:50"]
        -453[n].452=36.448      <EnteringTrader> [default; randon string, i.e; ":randstr:50"]
        -453[n].452=54.448      <SenderLocation> [default; randon string, i.e; ":randstr:50"]
        -1                      <AccountId> [default; randon string, i.e; ":randstr:30"]
        -5149                   <Memo> [default; randon string, i.e; ":randstr:26"]
        -31                     <Price> [default; random bigdecimal, i.e; ":randbigdec:2"]
        -32                     <Quantity> [default; random integer, i.e; ":randint:"]
        -336                    <TradingSessionID> [default; randon string, i.e; ":randstr:30"]
        -625                    <TradingSessionSubID> [default; randon string, i.e; ":randstr:30"]
        -6392                   <SecurityTradingStatusID> [default; randon string, i.e; ":randstr:30"]
        -382=[0].375            <CounterPartyBroker> [default; randon string, i.e; ":randstr:255"]
        -1115                   <OrderCategory> [default; randon string, i.e; ":randstr:30"]
        -453=[n].452=4.448      <ClearingFirmCode> [default; randon string, i.e; ":randstr:26"]
        -453=[n].452=5.448      <InvestorID> [default; randon string, i.e; ":randstr:50"]
        -453=[n].452=17.448     <ContraFirmCode> [default; randon string, i.e; ":randstr:50"]
        -453=[n].452=76.448     <DeskID> [default; randon string, i.e; ":randstr:50"]
        -38                     <OrderQuantity> [default; random integer, i.e; ":randint:"]
        -44                     <OrderPrice> [default; random bigdecimal, i.e; ":randbigdec:2"]
        -432                    <OrderExpireDate> [default; today as local date, i.e;":today:YYYYMMdd"]
        -41                     <OriginalClientOrderID> [default; randon string, i.e; ":randstr:38"]
        -526                    <SecondaryClientOrderID> [default; randon string, i.e; ":randstr:38"]
        -14                     <FilledQuantity> [default; random integer, i.e; ":randint:"]
        -40                     <OrderType> [default; random integer, i.e; ":randint:"]
        -424                    <DayOrderQuantity> [default; random integer, i.e; ":randint:"]
        -55                     <Ticker> [default; randon string, i.e; ":randstr:20"]