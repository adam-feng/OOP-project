package unsw.loopmania;

/**
 * Goal expression
 */
public abstract class GoalExpression {
    /**
     * Evaluate goal
     * @return boolean
     */
    public abstract boolean evaluate();

    /**
     * Print goal
     * @return String
     */
    public abstract String prettyPrint();
}
