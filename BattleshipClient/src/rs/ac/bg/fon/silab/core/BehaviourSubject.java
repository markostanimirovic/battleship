/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import rs.ac.bg.fon.silab.dto.ServerDto;

/**
 *
 * @author marko
 */
public abstract class BehaviourSubject {

    private List<Consumer<ServerDto>> consumers;
    private ServerDto lastValue;

    protected BehaviourSubject() {
        consumers = new ArrayList<>();
    }
    
    public void subscribe(Consumer<ServerDto> consumer) {
        if (lastValue != null) {
            consumer.accept(lastValue);
        }
        consumers.add(consumer);
    }

    public void next(ServerDto serverDto) {
        lastValue = serverDto;
        consumers.forEach(consumer -> {
            consumer.accept(serverDto);
        });
    }

}
