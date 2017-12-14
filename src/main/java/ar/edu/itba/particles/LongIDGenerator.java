package ar.edu.itba.particles;

public class LongIDGenerator implements IDGenerator{
    private Long counter;

    public LongIDGenerator(){
        this.counter = 0L;
    }

    @Override
    public String getID() {
        this.counter++;
        return this.counter.toString();
    }
}
