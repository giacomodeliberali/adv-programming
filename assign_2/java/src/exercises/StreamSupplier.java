package exercises;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author giacomodeliberali
 */
public class StreamSupplier<T> {
    
    Collection<T> collection;

    public StreamSupplier(Stream<T> stream) {
        this.collection = stream.collect(Collectors.toList());;
    }  
    
    public Stream<T> get(){
        return collection.stream();
    }
}