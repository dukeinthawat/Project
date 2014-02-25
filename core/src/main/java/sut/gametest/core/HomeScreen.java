package sut.gametest.core;

import playn.core.Font;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;




import static playn.core.PlayN.graphics;

public class HomeScreen extends UIScreen {

        public static final Font title_font = graphics().createFont(
                            "Helvetica", playn.core.Font.Style.PLAIN,24);

        private final ScreenStack ss;

        private Root root;

        public HomeScreen(ScreenStack ss) {
            this.ss = ss;
        }

    @Override
    public void wasShown() {
        super.wasShown();
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(),layer);
        root.addStyles(Style.BACKGROUND
                .is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5)
                        .inset(5,10)));
        root.setSize(width(), height());
        root.add(new Label("Welcome to Home Screen").addStyles(Style.FONT.is(HomeScreen.title_font)));
        root.add(new Button("Next Screen").onClick(new UnitSlot() {
            public void onEmit(){
                ss.push(new SecondScreen(ss));
            }
        }));
    }
}



