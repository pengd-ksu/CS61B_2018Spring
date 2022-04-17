public class NBody {
    public static double readRadius (String filename) {
        In in = new In (filename);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet [] readPlanets (String filename) {
        In in = new In (filename);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet [] planets = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double vx = in.readDouble();
            double vy = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xp, yp, vx, vy, m, img);
        }
        return planets;
    }

    public static void main(String args[]) {
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet [] planets = readPlanets(filename);

        StdDraw.setXscale(-r, r);
        StdDraw.setYscale(-r, r);

        double time = 0;
        int num = planets.length;
        StdDraw.enableDoubleBuffering();

        while (time <= T) {
            double [] xForces = new double[num];
            double [] yForces = new double[num];

            for (int i = 0; i < num; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < num; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // draw the background picture
            StdDraw.picture(0, 0, "images/starfield.jpg");

            // draw all the planets
            for (Planet p: planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName); 
        }
    }
}