package lotto.service;

import lotto.Ranking;
import lotto.domain.Lotto;
import lotto.model.Computer;
import lotto.model.Player;

public class MatchLotteryService {

    private static final int THREE_MATCH = 3;
    private static final int FOUR_MATCH = 4;
    private static final int FIVE_MATCH = 5;
    private static final int SIX_MATCH = 6;

    private final Player player;
    private final Computer computer;

    public MatchLotteryService(Player player, Computer computer){
        this.player= player;
        this.computer = computer;
    }

    public void matchPlayerWithComputer(){
        for(Lotto lottoEach : player.getPlayerLotto()){
            matchEachTicketWithComputer(lottoEach);
        }
    }

    private void matchEachTicketWithComputer(Lotto lottoEach){
        int equalCount = countContainingNumber(lottoEach);
        player.addLottoRanking(convertCountToRanking(lottoEach, equalCount));
    }

    private int countNumberIfContains(int numberEach){
        if(computer.getComputerNumber().getNumbers().contains(numberEach)) return 1;
        return 0;
    }

    private int countContainingNumber(Lotto lottoEach){
        int countingResult = 0;
        for(int  numberEach : lottoEach.getNumbers()){
            countingResult += countNumberIfContains(numberEach);
        }
        return countingResult;
    }

    private Ranking convertCountToRanking(Lotto lottoEach, int equalCount){
        if(equalCount == SIX_MATCH) return Ranking.SIX_MATCH;
        if(equalCount == FIVE_MATCH) return separateByBonusNumber(lottoEach);
        if(equalCount == FOUR_MATCH) return Ranking.FOUR_MATCH;
        if(equalCount == THREE_MATCH) return Ranking.THREE_MATCH;
        return Ranking.NOTHING;
    }

    private Ranking separateByBonusNumber(Lotto lottoEach){
        if(lottoEach.getNumbers().contains(computer.getBonusNumber())) return Ranking.FIVE_MATCH_WITH_BONUS;
        return Ranking.FIVE_MATCH;
    }
}
