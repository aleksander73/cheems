package aleksander73.cheems.animation;

import aleksander73.cheems.utility.functional_interface.Function;

public class ValueAnimation<O> extends Animation {
    private Function<Float, O> f;

    public ValueAnimation(float duration, boolean looped) {
        super(duration, looped);
    }

    public ValueAnimation(float duration, boolean looped, Function<Float, O> f) {
        super(duration, looped);
        this.f = f;
    }

    public O value() {
        return f.accept(this.getT());
    }

    public void setF(Function<Float, O> f) {
        this.f = f;
    }
}
