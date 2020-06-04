/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.editor;
import textgame.structure.actions.ExitGame;
import textgame.structure.actions.LoadGame;
import textgame.structure.actions.RemoveItemFromInventory;
import textgame.structure.Game;
import textgame.structure.GameEventListener;
import textgame.structure.Item;
import textgame.structure.Option;
import textgame.structure.Player;
import textgame.structure.Room;
import textgame.structure.StaticObject;
import textgame.structure.actions.AddItemToRoom;
import textgame.structure.actions.AddOptionToRoom;
import textgame.structure.actions.AddPathFromRoom;
import textgame.structure.actions.AddToCustomValue;
import textgame.structure.actions.ChangeRoomDescription;
import textgame.structure.actions.ChangeStaticObjectDescription;
import textgame.structure.actions.MovePlayerToRoom;
import textgame.structure.actions.PushMessage;
import textgame.structure.actions.RemoveOptionFromRoom;
import textgame.structure.actions.RemoveStaticObjectFromRoom;
import textgame.structure.actions.SetCustomValue;
import textgame.structure.actions.EnableGameEventListener;
import textgame.structure.actions.DisableGameEventListener;
import textgame.structure.gameEvents.GameEvent;
/**
 *
 * @author david
 */
public class TestGameFactory {
    public static Game create_IndianJohn(){
        Game result = Game.getInstance(true);
        result.setName("Test game - Indian John");
        Room velkaHala = result.addNewRoom();
        Room poklad = result.addNewRoom();
        Room arena = result.addNewRoom();
        Room hanojskeVeze = result.addNewRoom();
        Room predRuinami = result.addNewRoom();
        Room boj = result.addNewRoom();
        Room gameOverRoom = result.addNewRoom();
        result.getPlayer().setCurrentRoom(predRuinami);
        
        Player p = result.getPlayer();
        p.addCustomValue("pocet_sosek_na_podstavci", 0);
        p.addCustomValue("bojovnik_zraneni", 0);
        p.addCustomValue("hrac_zraneni", 0);
        //---VCHOD-DO-RUIN------------------------------------------------------
        predRuinami.setName("Vchod do ruin");
        predRuinami.setDescription("Stojíš před vchodem do tajemných aztéckých "
                + "ruin. Vchod je bohužel blokovaný závalem. Je tu starý"
                + " opuštění kemp, který tu zanechaly předchozí dobrodruzi.");
        StaticObject brasna = result.addNewStaticObject();
        brasna.setName("Brašna");
        brasna.setDesctiption("Stará potrhaná kožená brašna. \n" +
                            "Našel jsi v ní nabitý trochu zašlý revolver\n" +
                            "a poznámkový blok s překladem starodávných jazků.");
        predRuinami.addStaticObjectToRoom(brasna, false);
        
        Item lopata = result.addNewItem();
        lopata.setName("Lopata");
        lopata.setDescription("Pevna lopata");
        predRuinami.addItem(lopata, false);
        
        Item lano = result.addNewItem();
        lano.setName("Lano");
        lano.setDescription("Pevne dlouhe lano.");
        predRuinami.addItem(lano, false);
        
        Item revolver = result.addNewItem();
        revolver.setName("Revolver");
        revolver.setDescription("Trochu orezlý, ale funkční a nabitý revolver.");
        predRuinami.addItem(revolver, false);
        
        Item poznamkovyBlok = result.addNewItem();
        poznamkovyBlok.setName("Poznamkovy blok");
        poznamkovyBlok.setDescription("Poznamkovy blok se starodávnými jazyky.");
        predRuinami.addItem(poznamkovyBlok, false);
        
        Option prohledejBrasnu = result.addNewOption();
        brasna.addOption(prohledejBrasnu);
        prohledejBrasnu.setLabel("Prohledej brasnu");
        prohledejBrasnu.addAction(new AddItemToRoom(predRuinami, revolver));
        prohledejBrasnu.addAction(new AddItemToRoom(predRuinami, poznamkovyBlok));
        prohledejBrasnu.addAction(new PushMessage("Prohledal jsi brašnu a našel jsi několik užitečných věcí."));
        prohledejBrasnu.addAction(new RemoveStaticObjectFromRoom(brasna, predRuinami));
        
        StaticObject zaval = result.addNewStaticObject();
        zaval.setName("Zaval");
        zaval.setDesctiption("Vchod do ruin je zavalaný. Se správným nářadím by se to možná dalo křekonat.");
        predRuinami.addStaticObjectToRoom(zaval, false);
        
        GameEventListener onLopataPickUp = result.addNewEventListener();
        onLopataPickUp.setName("On Lopata Pick up");
        onLopataPickUp.setExpectedvEventType(GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY);
        onLopataPickUp.setExpectedValues(new Object[]{lopata});
        
        Option prehodLano = result.addNewOption();
        Option prokopejZaval = result.addNewOption();
        prehodLano.setLabel("Prokopej zával");
        prehodLano.addAction(new AddPathFromRoom(predRuinami, velkaHala));
        prehodLano.addAction(new PushMessage("Přivázal jsi kámen na konec provazu jako závaži a hodil jej přes zával."
                + " Kámen se za něco zachytil a nyní funguje jako kotva. Nyní múžeš zával přelést"));
        prehodLano.addAction(new RemoveItemFromInventory(lano));
        prehodLano.addAction(new RemoveOptionFromRoom(prehodLano, predRuinami));
        prehodLano.addAction(new RemoveOptionFromRoom(prokopejZaval, predRuinami));
        
        prokopejZaval.setLabel("Prokopej zával");
        prokopejZaval.addAction(new AddPathFromRoom(predRuinami, velkaHala));
        prokopejZaval.addAction(new PushMessage("Prokopal jsi se skrz zával a nyní lze projít."
                + " Bohužel lopata se při popání zničila"));
        prokopejZaval.addAction(new RemoveItemFromInventory(lopata));
        prokopejZaval.addAction(new RemoveOptionFromRoom(prokopejZaval, predRuinami));
        prokopejZaval.addAction(new RemoveOptionFromRoom(prehodLano, predRuinami));
        onLopataPickUp.addAction(new AddOptionToRoom(prokopejZaval, predRuinami));
        //---VELKA-HALA---------------------------------------------------------
        velkaHala.setName("Velká hala");
        velkaHala.setDescription("Velká hala zdobéná starodávnými bohy. Jsou tu velké dveře a dva podstavce.");
        
        velkaHala.addPath(predRuinami);
        velkaHala.addPath(arena);
        velkaHala.addPath(hanojskeVeze);
        
        StaticObject podstavce = result.addNewStaticObject();
        podstavce.setName("Kamenné podstavce");
        podstavce.setDesctiption("Dva kamenné podstavce ve výši pasu. Vypadají, že na ně patří něco jako malá soška nebo louče.");
        velkaHala.addStaticObjectToRoom(podstavce, false);
        
        StaticObject velkeDvere = result.addNewStaticObject();
        velkeDvere.setName("Velké dveře");
        velkeDvere.setDesctiption("Velké dveře popsané starodávným jazykem.");
        velkaHala.addStaticObjectToRoom(velkeDvere, false);
        
        
        GameEventListener onPoznamkovyBlockPickUp = result.addNewEventListener();
        onPoznamkovyBlockPickUp.setName("On poznamkovy blok pick up");
        onPoznamkovyBlockPickUp.setExpectedvEventType(GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY);
        onPoznamkovyBlockPickUp.setExpectedValues(new Object[]{poznamkovyBlok});
        onPoznamkovyBlockPickUp.addAction(new ChangeStaticObjectDescription(velkeDvere,""
                + "Velké dveře popsané starodávným jazykem."
                + "Díky poznámkovému bloku jsi byl schopen přeložit nápis:\n\n"
                + "\"Pouze moudří a silní smí vstoupit.\""));
        
        Item soskaMedveda= result.addNewItem();
        soskaMedveda.setName("Soška medveda");
        soskaMedveda.setDescription("Malá soška zuřícího medvěda vytesaná z kamene.");
        
        Option polozSoszkuMedvedaNaPodstavec = result.addNewOption();
        polozSoszkuMedvedaNaPodstavec.setLabel("Postav sošku medvěda na podstavec");
        polozSoszkuMedvedaNaPodstavec.addAction(new AddToCustomValue("pocet_sosek_na_podstavci", 1));
        polozSoszkuMedvedaNaPodstavec.addAction(new PushMessage("Polozil jsi sosku medveda na podstavec."));
        polozSoszkuMedvedaNaPodstavec.addAction(new RemoveOptionFromRoom(polozSoszkuMedvedaNaPodstavec, velkaHala));
        polozSoszkuMedvedaNaPodstavec.addAction(new RemoveItemFromInventory(soskaMedveda));
        
        Item soskaSovy= result.addNewItem();
        soskaSovy.setName("Soška sovy");
        soskaSovy.setDescription("Malá soška moudré sovy vytesaná z kamene.");
        
        Option polozSoskuSovyNaPodstavec = result.addNewOption();
        polozSoskuSovyNaPodstavec.setLabel("Postav sošku sovy na podstavec");
        polozSoskuSovyNaPodstavec.addAction(new AddToCustomValue("pocet_sosek_na_podstavci", 1));
        polozSoskuSovyNaPodstavec.addAction(new PushMessage("Polozil jsi sosku sovy na podstavec."));
        polozSoskuSovyNaPodstavec.addAction(new RemoveOptionFromRoom(polozSoskuSovyNaPodstavec, velkaHala));
        polozSoskuSovyNaPodstavec.addAction(new RemoveItemFromInventory(soskaSovy));
        
        GameEventListener onSoskaMedvedaPickUp = result.addNewEventListener();
        onSoskaMedvedaPickUp.setName("On Soska medveda pick up");
        onSoskaMedvedaPickUp.setExpectedValues(new Object[]{soskaMedveda});
        onSoskaMedvedaPickUp.setExpectedvEventType(GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY);
        onSoskaMedvedaPickUp.addAction(new AddOptionToRoom(polozSoszkuMedvedaNaPodstavec, velkaHala));
        
        GameEventListener onSoskaSovyPickUp = result.addNewEventListener();
        onSoskaSovyPickUp.setName("On Soska sovy pick up");
        onSoskaSovyPickUp.setExpectedValues(new Object[]{soskaSovy});
        onSoskaSovyPickUp.setExpectedvEventType(GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY);
        onSoskaSovyPickUp.addAction(new AddOptionToRoom(polozSoskuSovyNaPodstavec, velkaHala));
        
       GameEventListener dveSoskyNaPodstavci =  result.addNewEventListener();
       dveSoskyNaPodstavci.setName("Dve sosky na podstavci");
       dveSoskyNaPodstavci.setExpectedValues(new Object[]{new Integer(2)});
       dveSoskyNaPodstavci.setExpectedvEventType(GameEvent.GameEventType.CUSTOM_VALUE_CHANGED);
       dveSoskyNaPodstavci.addAction(new PushMessage("Jakmile druhá soška dosedne na podstavec, velké dveře se otevřeli."));
       dveSoskyNaPodstavci.addAction(new AddPathFromRoom(velkaHala,poklad));
       
       //---ARENA---------------------------------------------------------------
       
       arena.setName("Aréna");
       arena.setDescription("Stojíš ve velké aréně. Podlaha je pokrytá jemným pískem, trinbuny jsou prázdné.\n\n"
               + "Uprostřed aréne stojí bojovník zahalený v černém plášti. Drží dva meče a jeden ti z nich podává.");
       StaticObject bojovnik = result.addNewStaticObject();
       bojovnik.setName("Bojovník");
       bojovnik.setDesctiption("Bojovník je zahalený c černém plášti. Na obličeji"
               + " má masku. Z jeho postoje máš pocit, že je prřipravený na boj,"
               + "ale dosud jen stojí uprostřed arény a podává ti jeden ze svých mečů."
               + " Za opaskem má malou sošku medvěda");
       Option vezmiMec = result.addNewOption();
       vezmiMec.setLabel("Vezmi meč.");
       vezmiMec.addAction(new MovePlayerToRoom(boj));
       arena.addOption(vezmiMec, false);
       
       Option bang = result.addNewOption();
       bang.setLabel("bang");
       bang.setLabel("Bang!");
       bang.addAction(new SetCustomValue("bojovnik_zraneni", 3));
       bang.addAction(new PushMessage("Vytáhl jsi svůj revolver a dříve než bojovník stihl jednat jsi vystřelil. Bojovník se kácí k zemi."));
       
       
       GameEventListener onRevolverPickUP = result.addNewEventListener();
       onRevolverPickUP.setName("On revolver pickp");
       onRevolverPickUP.setExpectedvEventType(GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY);
       onRevolverPickUP.setExpectedValues(new Object[]{revolver});
       onRevolverPickUP.addAction(new AddOptionToRoom(bang, arena));
       //---BOJ-----------------------------------------------------------------
       boj.setName("Boj");
       boj.setDescription("Stojíš proti bojovníkovi se svým mečem připraveným k boji. Bojovník kolem tebe krouří a čeká na svůj momenet.");
       boj.addStaticObjectToRoom(bojovnik, false);
       
       Option utok = result.addNewOption();
       utok.setLabel("Utok");
       Option obrana = result.addNewOption();
       obrana.setLabel("Obrana");
       Option kop = result.addNewOption();
       kop.setLabel("Kop");
       
       GameEventListener bojovnikKopUtok = result.addNewEventListener();
       GameEventListener bojovnikKopObrana = result.addNewEventListener();
       GameEventListener bojovnikKopKop= result.addNewEventListener();
       
       GameEventListener bojovnikObranaUtok = result.addNewEventListener();
       GameEventListener bojovnikObranaObrana = result.addNewEventListener();
       GameEventListener bojovnikObranaKop = result.addNewEventListener();
       
       
       GameEventListener bojovnikUtokKop = result.addNewEventListener();
       GameEventListener bojovnikUtokObrana = result.addNewEventListener();
       GameEventListener bojovnikUtokUtok = result.addGameEventListener("Bojovnik-UtokVSUtok", 
               GameEvent.GameEventType.OPTION_SELECTED,
               new Object[]{utok}, new AddToCustomValue("hrac_zraneni", 1));
       bojovnikUtokUtok.addAction(new AddToCustomValue("bojovnik_zraneni",1));
       bojovnikUtokUtok.addAction(new PushMessage("Bojovnik na tebe zaútočil a zranil tě,"
               + " avšak když se pokusil ustopit do bezpečné vzdálenosti, "
               + "odpověděl jsi vlastním a zranil jsi bojovníka dříve než se stihl stáhnout."));
       bojovnikUtokUtok.addAction(new EnableGameEventListener(bojovnikObranaUtok));
       bojovnikUtokUtok.addAction(new EnableGameEventListener(bojovnikObranaObrana));
       bojovnikUtokUtok.addAction(new EnableGameEventListener(bojovnikObranaKop));
       
       bojovnikUtokUtok.addAction(new DisableGameEventListener(bojovnikUtokUtok));
       bojovnikUtokUtok.addAction(new DisableGameEventListener(bojovnikUtokObrana));
       bojovnikUtokUtok.addAction(new DisableGameEventListener(bojovnikUtokKop));
       
       bojovnikUtokObrana.setName("Bojovnik-UtokVSObrana");
       bojovnikUtokObrana.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikUtokObrana.setExpectedValues(new Object[]{obrana});
       bojovnikUtokObrana.addAction(new AddToCustomValue("bojovnik_zraneni", 1));
       bojovnikUtokObrana.addAction(new PushMessage("Bojovnik na tebe zaútočil a potkal tvojí pohotovou obranu. "
                + "Bezpečně jsi jeho útok odrazil a zásáhl ho protiútokem. Bojovník jsi zranil."));
        
       bojovnikUtokObrana.addAction(new EnableGameEventListener(bojovnikObranaUtok));
       bojovnikUtokObrana.addAction(new EnableGameEventListener(bojovnikObranaObrana));
       bojovnikUtokObrana.addAction(new EnableGameEventListener(bojovnikObranaKop));
       
       bojovnikUtokObrana.addAction(new DisableGameEventListener(bojovnikUtokUtok));
       bojovnikUtokObrana.addAction(new DisableGameEventListener(bojovnikUtokObrana));
       bojovnikUtokObrana.addAction(new DisableGameEventListener(bojovnikUtokKop));
       
       bojovnikUtokKop.setName("Bojovnik-UtokVSKop");
       bojovnikUtokKop.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikUtokKop.setExpectedValues(new Object[]{kop});
       bojovnikUtokKop.addAction(new AddToCustomValue("hrac_zraneni", 1));
       bojovnikUtokKop.addAction(new PushMessage("Bojovnik na tebe zaútočil , "
                + "zasáhl tvojí nohu z bezpečné vzdálenosti a zranil tě."));
        
       bojovnikUtokKop.addAction(new EnableGameEventListener(bojovnikObranaUtok));
       bojovnikUtokKop.addAction(new EnableGameEventListener(bojovnikObranaObrana));
       bojovnikUtokKop.addAction(new EnableGameEventListener(bojovnikObranaKop));
       
       bojovnikUtokKop.addAction(new DisableGameEventListener(bojovnikUtokUtok));
       bojovnikUtokKop.addAction(new DisableGameEventListener(bojovnikUtokObrana));
       bojovnikUtokKop.addAction(new DisableGameEventListener(bojovnikUtokKop));
       
       
       bojovnikObranaUtok.setName("Bojovnik-ObranaVSUtok");
       bojovnikObranaUtok.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikObranaUtok.setExpectedValues(new Object[]{utok});
       bojovnikObranaUtok.addAction(new AddToCustomValue("hrac_zraneni", 1));
       bojovnikObranaUtok.addAction(new PushMessage("Bojovnik nasadil tvrdou obranu, odrazil tvůj útok a "
                + "odpověděl rychlým proti útokem. Zranil tě."));
       
       bojovnikObranaUtok.addAction(new EnableGameEventListener(bojovnikKopUtok));
       bojovnikObranaUtok.addAction(new EnableGameEventListener(bojovnikKopObrana));
       bojovnikObranaUtok.addAction(new EnableGameEventListener(bojovnikKopKop));
       
       bojovnikObranaUtok.addAction(new DisableGameEventListener(bojovnikObranaUtok));
       bojovnikObranaUtok.addAction(new DisableGameEventListener(bojovnikObranaObrana));
       bojovnikObranaUtok.addAction(new DisableGameEventListener(bojovnikObranaKop));
       
       
       bojovnikObranaObrana.setName("Bojovnik-ObranaVSObrana");
       bojovnikObranaObrana.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikObranaObrana.setExpectedValues(new Object[]{obrana});
       bojovnikObranaObrana.addAction(new PushMessage("Bojovnik nasadil tvrdou obranu a ty jsi odpověděl svou vlastní."
               + " Cvíli na sebe koukáte než si uvědomíte, že bránit se nemá smysl, když na tebe nikdo neútočí."));
       
       bojovnikObranaObrana.addAction(new EnableGameEventListener(bojovnikKopUtok));
       bojovnikObranaObrana.addAction(new EnableGameEventListener(bojovnikKopObrana));
       bojovnikObranaObrana.addAction(new EnableGameEventListener(bojovnikKopKop));
       
       bojovnikObranaObrana.addAction(new DisableGameEventListener(bojovnikObranaUtok));
       bojovnikObranaObrana.addAction(new DisableGameEventListener(bojovnikObranaObrana));
       bojovnikObranaObrana.addAction(new DisableGameEventListener(bojovnikObranaKop));
       
       bojovnikObranaKop.setName("Bojovnik-ObranaVSKop");
       bojovnikObranaKop.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikObranaKop.setExpectedValues(new Object[]{kop});
       bojovnikObranaKop.addAction(new AddToCustomValue("bojovnik_zraneni", 1));
       bojovnikObranaKop.addAction(new PushMessage("Bojovnik nasadil tvrdou obranu"
               + " a ty jsi jí prorazil dobře načasovaným kopem, který bojovníka"
               + " vyvedl z rovnováhy. Než bojovník našel svoji rovnováhu stihl "
               + "jsi ho zasáhnout rychým útokem a zranil jsi ho."));
       
       bojovnikObranaKop.addAction(new EnableGameEventListener(bojovnikKopUtok));
       bojovnikObranaKop.addAction(new EnableGameEventListener(bojovnikKopObrana));
       bojovnikObranaKop.addAction(new EnableGameEventListener(bojovnikKopKop));
       
       bojovnikObranaKop.addAction(new DisableGameEventListener(bojovnikObranaUtok));
       bojovnikObranaKop.addAction(new DisableGameEventListener(bojovnikObranaObrana));
       bojovnikObranaKop.addAction(new DisableGameEventListener(bojovnikObranaKop));
       
       bojovnikKopUtok.setName("Bojovnik-KopVSUtok");
       bojovnikKopUtok.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikKopUtok.setExpectedValues(new Object[]{utok});
       bojovnikKopUtok.addAction(new AddToCustomValue("bojovnik_zraneni", 1));
       bojovnikKopUtok.addAction(new PushMessage("Bojovník se tě pokusil vyvést"
               + " z rovnováhy kopem. Tím ale odhalil svoji nohu a ty jsi bezpečně"
               + " nohu zasáhl útokem a bojovníka zranil."));
       
       bojovnikKopUtok.addAction(new EnableGameEventListener(bojovnikUtokUtok));
       bojovnikKopUtok.addAction(new EnableGameEventListener(bojovnikUtokObrana));
       bojovnikKopUtok.addAction(new EnableGameEventListener(bojovnikUtokKop));
       
       bojovnikKopUtok.addAction(new DisableGameEventListener(bojovnikKopUtok));
       bojovnikKopUtok.addAction(new DisableGameEventListener(bojovnikKopObrana));
       bojovnikKopUtok.addAction(new DisableGameEventListener(bojovnikKopKop));
       
       bojovnikKopObrana.setName("Bojovnik-KopVSObrana");
       bojovnikKopObrana.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikKopObrana.setExpectedValues(new Object[]{obrana});
       bojovnikKopObrana.addAction(new AddToCustomValue("hrac_zraneni", 1));
       bojovnikKopObrana.addAction(new PushMessage("Bojovník tě kopnutím vyvedl z rovnováhy a tím překonal tvojí obranu."
               + "Něž si svojí rovnováhu znovu našel zranil tě rychlým útokem."));
       
       bojovnikKopObrana.addAction(new EnableGameEventListener(bojovnikUtokUtok));
       bojovnikKopObrana.addAction(new EnableGameEventListener(bojovnikUtokObrana));
       bojovnikKopObrana.addAction(new EnableGameEventListener(bojovnikUtokKop));
       
       bojovnikKopObrana.addAction(new DisableGameEventListener(bojovnikKopUtok));
       bojovnikKopObrana.addAction(new DisableGameEventListener(bojovnikKopObrana));
       bojovnikKopObrana.addAction(new DisableGameEventListener(bojovnikKopKop));
       
       
       bojovnikKopKop.setName("Bojovnik-KopVSKop");
       bojovnikKopKop.setExpectedvEventType(GameEvent.GameEventType.OPTION_SELECTED);
       bojovnikKopKop.setExpectedValues(new Object[]{obrana});
       bojovnikKopKop.addAction(new PushMessage("Bojovník se tě pokusil vyvést"
               + " z rovnováhy kopem a ty jsi opověděl kopem vlastním. Oba jste"
               + " ztratily rovnováhu a ani jeden z vás nebyl schopen této situace využít."));
       
       bojovnikKopKop.addAction(new EnableGameEventListener(bojovnikUtokUtok));
       bojovnikKopKop.addAction(new EnableGameEventListener(bojovnikUtokObrana));
       bojovnikKopKop.addAction(new EnableGameEventListener(bojovnikUtokKop));
       
       bojovnikKopKop.addAction(new DisableGameEventListener(bojovnikKopUtok));
       bojovnikKopKop.addAction(new DisableGameEventListener(bojovnikKopObrana));
       bojovnikKopKop.addAction(new DisableGameEventListener(bojovnikKopKop));
       
       GameEventListener bojovnikMrtev = result.addNewEventListener();
       bojovnikMrtev.setName("Bojovnik Mrtev");
       bojovnikMrtev.setExpectedvEventType(GameEvent.GameEventType.CUSTOM_VALUE_CHANGED);
       bojovnikMrtev.setExpectedValues(new Object[]{"bojovnik_zraneni", new Integer(3)});
       bojovnikMrtev.addAction(new MovePlayerToRoom(arena));
       bojovnikMrtev.addAction(new AddItemToRoom(arena, soskaMedveda));
       bojovnikMrtev.addAction(new ChangeRoomDescription(arena, "Stojíš ve velké aréně. "
               + "Podlaha je pokrytá jemným pískem a krví, trinbuny jsou prázdné.\n\n"
               + "Uprostřed aréne nehybně leží zkrvavený bojovník zahalený v černém plášti."));
       bojovnikMrtev.addAction(new ChangeStaticObjectDescription(bojovnik,"Bojovník je zahalený c černém plášti. Na obličeji"
               + " má masku. Leží nehybně na podlaze celý od krve." ));
        //-----GAME-OVER-------------------------------------------------------------
        gameOverRoom.setName("Hra končí");
        gameOverRoom.setDescription("Hra končí, bohužel jsi souboj nepřežil a poklad zůstává neodhalen");
        
        Option loadGame = result.addNewOption();
        loadGame.setLabel("Load game");
        loadGame.addAction(new LoadGame());
        
        Option exitGame = result.addNewOption();
        exitGame.setLabel("Exit game");
        exitGame.addAction(new ExitGame());
        
        gameOverRoom.addOption(loadGame, true);
        gameOverRoom.addOption(exitGame, true);
        //-----POKLAD-----------------------------------------------------------
        poklad.setName("Poklad");
        poklad.setDescription("Stojíš v místnosti plné zlata, oči ti přecházejí"
                + " nad tou krásou a chvíli jen tak stojíš a bodivuješ tu krásu.\n\n"
                + "Konec hry\n Vyhrál jsi!");
        poklad.addOption(exitGame, true);
       
       
       //---HANOJSKE-VEZE-------------------------------------------------------
       hanojskeVeze.setName("Hanojské věže");
       hanojskeVeze.setDescription("Jsi v místonsti a pře tebou stojí tři velké "
               + "pilíře a na prvním z nich jsou navléknuté 3 kamené obruče různých velikostí. Největší ve spod a nejmenší navrchu."
               + " Všiml jsi, že ty větší levitují ty menší.");
       hanojskeVeze.addPath(velkaHala);
       hanojskeVeze.addItem(soskaSovy, false);
       return result;
    }
}
