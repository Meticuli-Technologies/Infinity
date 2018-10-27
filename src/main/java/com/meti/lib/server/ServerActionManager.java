package com.meti.lib.server;

import com.meti.lib.manage.Manager;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ServerActionManager extends Manager<Predicate<String>, Consumer<String>> {
    public abstract static class RegexPredicate implements Predicate<String> {
        public static RegexPredicate of(String regex){
            return new RegexPredicate() {
                @Override
                protected String getRegex() {
                    return regex;
                }
            };
        }

        protected abstract String getRegex();

        @Override
        public boolean test(String s) {
            return s.matches(getRegex());
        }
    }
}
