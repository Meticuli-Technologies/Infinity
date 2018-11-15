package com.meti.app;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public interface  Evaluator {
    boolean canEvaluate(Object obj);
    void evaluate(Object obj) throws Exception;
}
