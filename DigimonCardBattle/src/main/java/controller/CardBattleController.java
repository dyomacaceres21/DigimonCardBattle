package controller;

import model.DigimonCard;
import model.BattleResult;
import service.CardBattleService;

public class CardBattleController {
    private final CardBattleService service = new CardBattleService();

    public BattleResult fight(DigimonCard c1, DigimonCard c2) {
        return service.battle(c1, c2);
    }
}