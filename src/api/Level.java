package api;

public enum Level
{
    EASY
            {
                @Override
                public String toString() {return "Łatwy";}
            },
    MEDIUM
            {
                @Override
                public String toString() {return "Średni";}
            },
    HARD
            {
                @Override
                public String toString() {return "Trudny";}
            },
    VERY_HARD
            {
                @Override
                public String toString() {return "Bardzo Trudny";}
            }
}