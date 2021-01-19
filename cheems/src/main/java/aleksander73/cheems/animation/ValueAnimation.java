package aleksander73.cheems.animation;

import androidx.arch.core.util.Function;

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
        return f.apply(this.getT());
    }

    public void setF(Function<Float, O> f) {
        this.f = f;
    }
}
