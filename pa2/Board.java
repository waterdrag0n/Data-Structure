public final class Board {
    private int width, height;
    private boolean[] board;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new boolean[width * height];

        for (int i = 0; i < board.length; i++) {
            board[i] = false;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isMissing(int i, int j) throws IndexOutOfBoundsException {
        return board[squareId(i, j)];
    }

    public int squareId(int i, int j) throws IndexOutOfBoundsException {
        if (i < 0 || j < 0 || i >= width || j >= height)
            throw new IndexOutOfBoundsException(String.format("(%d, %d)", i, j));
        return i * height + j;
    }

    public void setMissing(int i, int j) throws IndexOutOfBoundsException {
        board[squareId(i, j)] = true;
    }
}
