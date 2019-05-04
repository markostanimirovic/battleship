/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.so.match;

import rs.ac.bg.fon.silab.database.DBBroker;
import rs.ac.bg.fon.silab.entity.Match;
import rs.ac.bg.fon.silab.so.BaseSO;

/**
 *
 * @author marko
 */
public class SaveMatchSO extends BaseSO<Match> {

    @Override
    protected void executeOperation(Match match) throws Exception {
        match.setMatchId(DBBroker.getInstance().getMaxIdentifierValue(match) + 1);
        DBBroker.getInstance().save(match);
    }

}
