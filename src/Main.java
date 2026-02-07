import java.sql.SQLException;

void main() throws IOException, SQLException {

    Repository r = new Repository();
    Webbshop ws = new Webbshop(r);
    ws.start();
}