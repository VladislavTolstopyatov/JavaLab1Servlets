package mappers;

public interface Imapper<F, T> {
    T map(F object);
}
