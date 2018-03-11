package model.expressions;

import model.utils.IDictionary;
import model.utils.IHeap;

public interface Expression {
    public int eval(IDictionary<String,Integer> d, IHeap<Integer, Integer> h);
}
