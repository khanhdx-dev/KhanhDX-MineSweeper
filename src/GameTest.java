import com.khanhdx.app.models.ScannerWrapper;
import com.khanhdx.app.services.impl.MineSweeperImpl;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    public void testGame(){
        ScannerWrapperMock scanner = new ScannerWrapperMock();
        MineSweeperImpl mineSweeper = new MineSweeperImpl();
        mineSweeper.setScanner(scanner);

        mineSweeper.start();
    }

    class ScannerWrapperMock extends ScannerWrapper {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public String next() {
            return "exit";
        }

        @Override
        public String nextLine() {
            i++;
            for (int j = 0; j < 3; j++) return "RA" + i;
            for (int j = 0; j < 3; j++) return "RB" + i;
            for (int j = 0; j < 3; j++) return "RC" + i;
            for (int j = 0; j < 3; j++) return "RD" + i;
            return null;
        }

        @Override
        public Integer nextInt() {
            return 3;
        }
    }
}


