
import java.math.BigDecimal;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AlexanderDT
 */
public class Heap {

    private Hotel first;
    private Hotel second;

    public Heap(Hotel first, Hotel second) {
        this.first = first;
        this.second = second;
    }

    public Hotel getFirst() {
        return first;
    }

    public void setFirst(Hotel first) {
        this.first = first;
    }

    public Hotel getSecond() {
        return second;
    }

    public void setSecond(Hotel second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.first);
        hash = 89 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Heap other = (Heap) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return ("\nFirst:" + new BigDecimal( first.getId()).toPlainString() + 
                "Second:" + new BigDecimal(second.getId()).toPlainString());

    }

}
