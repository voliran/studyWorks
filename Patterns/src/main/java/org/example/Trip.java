package org.example;

import java.io.*;

class Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    private int duration;
    private String destination;

    public Trip() {}

    private Trip(Builder builder) {
        this.destination = builder.destination;
        this.duration = builder.duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getDestination() {
        return destination;
    }

    public static class Builder {
        private int duration;
        private String destination;

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}