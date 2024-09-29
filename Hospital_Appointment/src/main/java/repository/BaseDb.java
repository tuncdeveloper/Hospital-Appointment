package repository;

public abstract class BaseDb {

    private ConnectDb connectDb ;

    public BaseDb () {
        connectDb = ConnectDb.getInstance() ;
    }

    public ConnectDb getConnectDb () {
        return connectDb ;
    }

}
