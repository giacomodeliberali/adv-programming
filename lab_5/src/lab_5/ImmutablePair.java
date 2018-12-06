/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_5;

/**
 *
 * @author giacomodeliberali
 */
public class ImmutablePair<T1, T2> {
    
    private T1 first;    
    private T2 second;

    public ImmutablePair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
    

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }
    
    

    
}
