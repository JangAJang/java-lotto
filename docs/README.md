# 기능목록
## 도메인 모델
### Lotto
1. validate : LottoException에서 validate한다. 
2. 생성자 : 정수형 리스트를 입력받아 validate후에 저장한다.

### BonusNumber
1. 생성자 : 정수형 입력변수를 저장한다. 컴퓨터 엔티티에서 로또번호에 포함되어있을 경우 예외처리
2. getBonusNumber : 저장한 정수형 변수를 리턴한다. 

### Profit
정수형 전역변수 payment, price를 가진다. 
1. checkPayment : 플레이어가 구매한 로또의 금액을 전역변수에 저장한다. 
2. addPrice : 입력변수 Ranking에 따른 상금을 price와 더해 저장한다.
3. getProfit : 입력변수로 당첨금액의 총합을 구해 로또 구매금액으로 백분율을 소수점 첫째 자리까지 리턴한다. 

### Winning
List<Ranking>를 가진다. 
1. addRankingOfPlayer : 입력변수 Ranking을 List<Ranking>에 add한다. 
2. figureSameRanking : List<Ranking>을 HashMap으로 바꾸어 리턴한다. 

## enum
### Ranking
THREE_MATCH, FOUR_MATCH, FIVE_MATCH, FIVE_MATCH_WITH_BONUS, SIX_MATCH를 가진다.

## 엔티티
### Player
List<Lotto>, Profit, Winning을 참조한다. 
1. buyTickets
   1. payTickets : Profit.checkPayment로 금액을 저장한다. 
   2. countTickets : 금액을 1,000으로 나눈 값을 리턴한다. 
   3. makeTickets : 입력변수로 받은 티켓의 개수만큼 로또를 만들어 List<Lotto>에 저장한다. 
2. showWinningStatistic : Winning.figureSameRanking를 리턴한다. 
3. getPlayersProfit : Profit.getProfit을 리턴한다. 
4. addLottoRanking
   1. addPlayersTicketRank : Winning.addRankingOfPlayer해준다. 
   2. addProfitOfTicket : 당첨 등급에 따른 금액을 Profit.addPrice해준다. 
5. getPlayerLotto : 플레이어의 로또 리스트를 반환한다. 

### Computer
Lotto, BonusNumber를 참조한다. 
1. saveWinningTicket : 문자열로 입력받은 값을 정수형 리스트로 바꾸어 저장한다. 
   1. splitTicket : ','로 문자열을 나누어 문자열의 배열로 리턴한다. 
   2. createTicket : 정수형 리스트로 바꾸어 Lotto의 생성자에 입력변수로 사용해 저장한다. 
2. saveBonusNumber : BonusNumber에 입력변수값을 정수형 변수로 저장한다. 
3. getComputerNumber : 컴퓨터의 당첨번호를 Lotto타입으로 반환한다. 
4. getBonusNumber : 보너스 넘버를 정수형으로 반환한다. 

## 예외처리
### LottoException
validate
1. isNotRightLength : 리스트의 길이가 6이 아니면 참을 반환
2. notRightLengthException : 맞지 않은 길이에 대한 예외처리
3. containsSameNumber : 리스트의 숫자를 셋에 담았을 때, 둘의 사이즈가 다르면 참을 반환
4. sameNumbersException : 같은 숫자를 가짐에 예외처리
5. containsNumberOutOfRange : 리스트의 숫자가 1부터 45사이가 아닌게 있으면 참을 반환
6. numberOutOfRangeException : 범위를 벗어난 숫자를 가짐에 예외처리

### PaymentException
1. validate
   1. isNotDividedByThousand : 1000으로 나누었을때 나머지가 0이 아니면 참을 반환
   2. paymentNotAcceptableException : 예외처리한다. 

## 서비스
### MatchLotteryService
1. matchPlayerWithComputer : 컴퓨터와 플레이어를 비교해 등수를 만들어준다. 
   1. matchEachTicketWithComputer : 컴퓨터와 동일하게 가지고 있는 숫자의 개수를 리턴한다.
   2. convertCountToRanking : 같은 숫자의 개수를 받아 Ranking으로 리턴시킨다. 
      1. isFiveMatch : 같은 숫자의 개수가 5개면 참을 반환한다. 
      2. seperateByBonusNumber
         1. containsBonusNumber : 로또가 보너스 넘버를 가지면 참을 반환한다.

## 뷰
### PlayerView
1. inquirePrice : '구입금액을 입력해 주세요.' 를 출력시킨다. 
2. scanPrice : 가격을 입력받아 문자열로 리턴시킨다.
3. validate : PaymentException을 통해 예외처리한다. 
4. showPlayersLotto : 플레이어 로또 구매 결과를 보여준다. 
   1. showPlayersLottoCount : 'n개를 구매했습니다.'를 출력한다. 
   2. showLottoNumbers : Lotto리스트를 한개씩 문자열로 출력한다. 

### LottoView
1. inquireNumbers : '당첨 번호를 입력해 주세요.'를 출력한다. 
2. scanNumbers : ','로 구분되는 숫자의 문자열을 반환한다. 
3. inquireBonusNumber : '보너스 번호를 입력해 주세요.'를 출력한다. 
4. scanBonusNumber : 보내스 숫자를 문자열로 반환한다.

### RankingView
1. startWinningStatistics : '당첨 통계\n---'를 출력한다. 
2. showPriceToPlayer : 'n개 일 (금액) - m개'를 금액이 낮은 것부터 순서대로 출력해준다.
3. showProfit : 실수형 입력변수를 '총 수익률은 x%입니다.' 를 출력한다. 

## Controller
### LotteryController