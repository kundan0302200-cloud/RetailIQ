package retailiq.co6;

/**
 * CO6: Brent's Theorem on Parallel Scheduling and Processor Bounds.
 * States: T_p <= (T_1 - T_inf)/p + T_inf <= T_1 / p + T_inf
 * Where:
 *   T_1 = Work (total operations)
 *   T_inf = Span (critical path length)
 *   p = number of parallel processors
 *   Parallelism = T_1 / T_inf
 */
public class BrentsTheorem {

    public static class BoundAnalysis {
        public double work;
        public double span;
        public int processors;
        public double theoreticalTimeBound;
        public double theoreticalSpeedup;
        public double maxParallelism;

        public BoundAnalysis(double work, double span, int processors, double theoreticalTimeBound,
                             double theoreticalSpeedup, double maxParallelism) {
            this.work = work;
            this.span = span;
            this.processors = processors;
            this.theoreticalTimeBound = theoreticalTimeBound;
            this.theoreticalSpeedup = theoreticalSpeedup;
            this.maxParallelism = maxParallelism;
        }
    }

    public static BoundAnalysis computeBounds(double work, double span, int processors) {
        if (processors <= 0) processors = 1;
        double tp = (work - span) / processors + span;
        double speedup = work / tp;
        double maxPar = span > 0 ? (work / span) : 1;
        return new BoundAnalysis(work, span, processors, tp, speedup, maxPar);
    }
}
