package dao;

public class DaoException extends Exception{

    private DaoExceptionType type;

    public DaoException(DaoExceptionType type, String message) {
        super( message );
        setType( type );
    }

    public DaoExceptionType getType() {
        return type;
    }

    private void setType(DaoExceptionType type) {
        this.type = type;
    }
}
