package it.ricci.game.Calcoli;

public class Trigonometria {

    private static float calcolaOrdinataAOrigine(float x1, float y1, float x2, float y2) {
        float m = calcolaPendenzaDellaRetta(x1, y1, x2, y2);
        return y1 - m * x1;
    }

    private static float calcolaPendenzaDellaRetta(float x1, float y1, float x2, float y2) {
        return (y2 - y1) / (x2 - x1);
    }

    public static float trovaYDaXDellaRetta(float x1, float y1, float x2, float y2, float xConosciuta) {

        float b = calcolaOrdinataAOrigine(x1, y1, x2, y2);
        float m = calcolaPendenzaDellaRetta(x1, y1, x2, y2);

        return m * xConosciuta + b;
    }

    public static float trovaXDaYDellaRetta(float x1, float y1, float x2, float y2, float yConosciuta) {
        float b = calcolaOrdinataAOrigine(x1, y1, x2, y2);
        float m = calcolaPendenzaDellaRetta(x1, y1, x2, y2);
        return (yConosciuta - b) / m;
    }


}
