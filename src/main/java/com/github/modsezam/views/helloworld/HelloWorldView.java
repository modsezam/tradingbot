package com.github.modsezam.views.helloworld;

import com.github.modsezam.service.BinanceClientWS;
import com.github.modsezam.service.BinanceClientWSMap;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.github.modsezam.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Locale;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class HelloWorldView extends VerticalLayout implements PollNotifier {

    private String pairSymbol;
    private String actualPrice;
    private String priceUnit;
    private Div priceDiv;

    private final BinanceClientWSMap binanceClientWSMap;


    public HelloWorldView(BinanceClientWSMap binanceClientWSMap) {
        this.binanceClientWSMap = binanceClientWSMap;

//        binanceClientWSService.createBoot("btcusdt");

        setId("hello-world-view");

//        HorizontalLayout helloWorldWrapper = new HorizontalLayout();
//        helloWorldWrapper.setWidth("100%");
//        helloWorldWrapper.setSpacing(true);
//        helloWorldWrapper.setMargin(true);
//
//        name = new TextField("Your name");
//        closeClient = new Button("Say hello");
//        add(new H3("TEST"));
//
//        add(name, closeClient);

//        setVerticalComponentAlignment(Alignment.END, name, closeClient);

//        closeClient.addClickListener(e -> {
//            Notification.show("Hello " + name.getValue());
//        });

//        setMaxWidth("1024px");
//        setAlignItems(Alignment.CENTER);


//        VerticalLayout helloWorldWrapper = new VerticalLayout();
//        helloWorldWrapper.setSizeFull();
//        helloWorldWrapper.setAlignItems(Alignment.CENTER);
//
//
//        HorizontalLayout pairSymbolWrapper = new HorizontalLayout();
//        pairSymbolWrapper.setAlignItems(Alignment.AUTO);
////        pairSymbolWrapper.setWidth("1000px");
//        pairSymbolWrapper.setWidth("1024px");
//
//        HorizontalLayout pairSymbolLayout = new HorizontalLayout();
//        pairSymbolLayout.setSizeFull();
//        pairSymbolLayout.setAlignItems(Alignment.END);
//        pairSymbolLayout.add(new H3("pair:"));;
//        H2 pairSymbol = new H2("BTCUSDT");
//        pairSymbolLayout.add(pairSymbol);
//
//        pairSymbolWrapper.add(pairSymbolLayout);
//        helloWorldWrapper.add(pairSymbolWrapper);
//        add(helloWorldWrapper);


//        HorizontalLayout layout = new HorizontalLayout();
//        layout.setWidth("400px");
//
//// These buttons take the minimum size.
//        layout.add(new Button("Small"));
//        layout.add(new Button("Medium-sized"));
//
//// This button will expand.
//        Button expandButton = new Button("Expanding component");
//
//// Use 100% of the expansion cell's width.
//        expandButton.setWidth("100%");
//
//// The component must be added to layout
//// before setting the ratio
//        layout.add(expandButton);
//
//        add(layout);

        pairSymbol = "BTCUSDT";
        actualPrice = "41,323.23";
        priceUnit = "USDT";

        add(pairSymbolLayout());
        add(actualPriceLayout());

//        TextField textField = new TextField();
//        textField.setValue(actualPrice);
//        textField.addValueChangeListener(event -> actualPrice = event.getValue());
//        add(textField);


//        getUI().get().access(() -> priceDiv.setText(actualPrice));

        Button closeClient = new Button("close client WS");
        add(closeClient);
        Button createClient = new Button("create client WS");
        add(createClient);

        closeClient.addClickListener(e -> {
            binanceClientWSMap.closeBinanceClientWS(pairSymbol);
        });
        createClient.addClickListener(e -> {
            binanceClientWSMap.createNewBinanceClientWS(pairSymbol);
        });

    }

    private HorizontalLayout pairSymbolLayout() {
        HorizontalLayout pairSymbolWrapper = new HorizontalLayout();
        pairSymbolWrapper.setClassName("pair-symbol-wrapper");

        Div pairDiv = new Div();
        pairDiv.setText("pair:");
        pairDiv.getStyle().set("font-size", "15px");
        Div symbolDiv = new Div();
        symbolDiv.setText(pairSymbol);
        symbolDiv.getStyle().set("font-size", "22px");

        pairSymbolWrapper.setAlignItems(Alignment.BASELINE);
        pairSymbolWrapper.add(pairDiv);
        pairSymbolWrapper.add(symbolDiv);
        return pairSymbolWrapper;
    }

    private HorizontalLayout actualPriceLayout() {
        HorizontalLayout actualPriceWrapper = new HorizontalLayout();
        actualPriceWrapper.setClassName("actual-price-wrapper");

        priceDiv = new Div();
        priceDiv.setText(actualPrice);
        priceDiv.setClassName("price-div");


        Div symbolDiv = new Div();
        symbolDiv.setText(priceUnit);
        symbolDiv.setClassName("symbol-div");

        actualPriceWrapper.setAlignItems(Alignment.BASELINE);
        actualPriceWrapper.add(priceDiv);
        actualPriceWrapper.add(symbolDiv);

        return actualPriceWrapper;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        System.out.println("attach");
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        System.out.println("detach");
    }

    @Scheduled(fixedDelay = 500)
    public void test() {
//        if (ui.isPresent()){
//            ui.get().access(() -> priceDiv.setText(actualPrice));
//            System.out.println("is present");
//        }
        if (getUI().isPresent()){
            getUI().get().access(() -> priceDiv.setText(binanceClientWSMap.getLastTradeEventPrice(pairSymbol)));
        }
//        getUI().get().access(() -> priceDiv.setText(actualPrice));
    }
}
