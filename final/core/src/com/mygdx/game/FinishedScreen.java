package com.mygdx.game;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
/**
 * a játék végén található képernyő
 * Tartalmazza a ranglista kezeléséhez szükséges dolgokat.
 * @author mark
 *
 */
public class FinishedScreen implements Screen {
	/**
	 * utalás a főprogramra, és ezáltal a "globális" változókra (DRY principle)
	 */
	final SilverTime game;
	/**
	 * a játék végén szerzett pontszám, másodpercben mérve. (milyen sokáig tartott elérni a célt)
	 */
	protected float newscore;
	/**
	 * Egy PCM audiólejátszó. a főmenüben felvett hangot visszajátssza. Hogyha nem volt felvéve hang, akkor egy-két másodpercig nem csinál semmit sem a program.
	 */
	protected AudioDevice player;
	/**
	 * le van-e már játszva a hang?
	 */
	protected boolean playedsound;
	/**
	 * cimke, ami írja a pontszámot.
	 */
	private Label label;
	/**
	 * cimke, ami tartalmazza a ranglistát
	 */
	private Label highscores;
	/**
	 * állomány amely tartalmazza a ranglistát
	 */
	private FileHandle fh;
	/**
	 * a ranglisán szereplő neve a játékosnak.
	 */
	private String name;

	/**
	 * Konstruktor. meg kell adni a főprogramra való utalást, és az elért pontszámot.
	 * @param game a főprogramra való utalás
	 * @param score a pontszám
	 */
	public FinishedScreen(final SilverTime game, float score) {
		this.game = game;
		label=new Label("A játék ideje: \n"+score,game.myskin);
		//A cimke szövege most be van állítva, mos már csak a helyzetét kell meghatározni.
		float y=game.stage.getViewport().getScreenHeight()/2;
		float x=game.stage.getViewport().getScreenWidth()/2;
		//Nagyjából három koordinátarendszerben dolgozik az ember egyszerre. Ezért fontos a megfelelőt kiválasztani.
		label.setPosition(x-label.getWidth()/2, y);
		//Most már a hely is be van állítva, és haladunk lefele a képernyőn. ( a 0,0 koórdináta a bal alsó sarok)
		y-=label.getHeight();
		game.stage.addActor(label); //ábrázolni  a cimkét.
		newscore=score; // elmentjük a pontszámot, habár később nem igazán használjuk. Talán egy későbbi pillanatban ki lesz vágva. 
		player=Gdx.audio.newAudioDevice(SilverTime.FREQ, true); // Az AudioDevice egy interface, ezért az adott metódusokat kell használni.
		playedsound=false; //még nem jótszottuk le a hangot.
		
		TextField input=new TextField("kérem a nevét írja be",game.myskin);
		input.setPosition(x-input.getWidth()/2,y); // középre helyezzük az elemeket.
		y-=input.getHeight();
		game.stage.addActor(input);
		// Egy adatbevivő elem, ahonnan a nevet lekérjük majd.
		
		fh=Gdx.files.local("highscores.txt");
		if(!fh.exists()) {
			fh.writeString("", false);
		}
		//Mivel később olvasni fogunk az állományból, biztosítjuk létezését.
		Button button=new TextButton("A név be van írva",game.myskin);
		button.addListener(new InputListener() {
			/**
			 * Be lett írva a név, ezért sok minden történik.
			 */
			@Override
			public boolean touchDown(InputEvent e, float x, float y, int pointer, int _button) {
				name=input.getText();
				Scanner scanner=new Scanner(fh.readString());
				// beolvassuk a ranglista tartalmát, majd a scanner elrendezi a parse-olását. 
				float[] arr=new float[10];
				String[] names=new String[10];
				String constr="";
				// Az újraépített ranglista (max 10 ember, megfelelő sorrendben, etc.)
				int i=0;
				boolean added=false;
				while(scanner.hasNext() && i<10) {
					String tmpstr=scanner.next();
					float tmpflt=scanner.nextFloat();
					//A formátum név [fehér köz] pontszám [\n]
					if(((tmpflt==0 /*talán bekerült egy nulla pontú ember, és ez lehetetlen. ki vele!*/ || tmpflt>newscore) && !added)){
						arr[i]=newscore;
						names[i++]=name.replace(' ', '_').replace('\t','_');//annak érdekében, hogy ne zavarja meg a scanner-t jövendőbeli nyereségek alkalmával.
						added=true;//csak egyszer szerepelhet a ranglistában egy egyén
					}
					arr[i]=tmpflt;
					names[i++]=tmpstr;
				}
				if(i==0) {
					arr[i]=newscore;
					names[i++]=name.replace(' ', '_').replace('\t','_');
					added=true;
				}//ha üres volt a ranglista, akkor nem volt mit olvasnia a scanner-nek. ezen esetet külön tárgyaltuk.
				i=0;//habár tudunk foreach ciklust használni, két listánk van.
				for(float f:arr) {
					if(f==0) {//vége a listánknak.
						break;
					}
					constr+=names[i]+"    "+f+"\n";
					i++;
				}
				highscores=new Label(constr,game.myskin);//felépítjük a cimkét, és újraíǎjuk a ranglistát.
				fh.writeString(constr, false);//overwrite entire file
				System.out.println(constr);//debugging information
				highscores.setSize(game.stage.getViewport().getScreenWidth(),game.stage.getViewport().getScreenHeight());//mindennek...
				highscores.setAlignment(Align.center);//...a közepén ...
				game.stage.addActor(highscores);//...van a ranglista, és ...
				input.addAction(Actions.removeActor());//...semmi más ...
				button.addAction(Actions.removeActor());//... nem szerepelhet ...
				label.addAction(Actions.removeActor());// ... a képernyőn.
				return true;//nem néztem bele ez mit jelent, de nem lényeges.
			}
		});
		button.setPosition(x-button.getWidth()/2, y);
		game.stage.addActor(button);
		//a gomb rákerül a képernyőre.
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//világos szürke a háttér
		game.stage.act(delta);
		game.stage.draw();
		//a UI elemeit lerajzoljuk
		if(!playedsound) {
			player.writeSamples(game.pcmaudio, 0, game.pcmaudio.length);
			playedsound=true;
			//lejátszük a hangot.
		}
	}

	@Override
	public void resize(int width, int height) {
		game.stage.getViewport().update(width, height, false);//fontos, hogy lehessen használni akkor is, ha változik a képernyő mérete.
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		for(Actor a:game.stage.getActors()) {
			a.addAction(Actions.removeActor());		
		}
		player.dispose();
	}

}
