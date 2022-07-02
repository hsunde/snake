class SelfCollisionException extends RuntimeException {
    public SelfCollisionException() {
        super("Snake collided with self");
    }
}
