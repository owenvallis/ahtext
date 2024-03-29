import processing.core.PApplet;
import processing.core.PGraphics;
import traer.physics.Particle;
import traer.physics.ParticleSystem;

public class ExplodingText {

	Particle[][] particles;
	ParticleSystem physics;
	PGraphics pg;
	AhTextContext ahTextContext;

	int numberOfColumns;
	int numberOfRows;
	int boundWidth;
	int boundHeight;
	char[][] characters;
	int z;

	public ExplodingText(PGraphics pg, AhTextContext ahTextContext){
		this.pg = pg;
		this.ahTextContext = ahTextContext;
		boundHeight = 24;


		physics = new ParticleSystem((float)1.0,(float).025);

		// find text bounding box width for the # of columns
		for (int x = 65; x < 89; x++) {

			if (ahTextContext.textWidth((char)x) > boundWidth) {
				boundWidth = (int)ahTextContext.textWidth((char)x);
			}
		}

		numberOfColumns = pg.width / boundWidth;
		numberOfRows = pg.height / boundHeight;

		characters = new char[numberOfRows][numberOfColumns];
		particles = new Particle[numberOfRows][numberOfColumns];
		
		for(int y = 0; y < numberOfRows; y++){
			for(int x = 0; x < numberOfColumns; x++){
				pg.fill(0);
				characters[y][x]  = (char)((int)ahTextContext.random(65, 88));
				particles[y][x] = physics.makeParticle( (float)1.0, (float)(x*boundWidth), y*boundHeight, 0 );
				if(x > 0){
					physics.makeAttraction( particles[y][x-1], particles[y][x], 10000 * ahTextContext.random(1), 10 );
				}
			}
		}
		 
	}

	public void drawXplode()
	{
		pg.beginDraw();
		pg.background(255);
		pg.noStroke();
		physics.tick();
		for(int x = 0; x < numberOfRows; x++){
			for(int y = 0; y < numberOfColumns; y++){
				handleBoundaryCollisions( particles[x][y]);
				pg.fill(10);			
				pg.text(characters[x][y],particles[x][y].position().x(), particles[x][y].position().y());
			}
		}
		pg.endDraw();
		ahTextContext.image(pg, 0, 0);
	}

	void handleBoundaryCollisions( Particle p )
	{
		if ( p.position().x() < 0 || p.position().x() > pg.width )
			p.velocity().set( (float)-0.9*p.velocity().x(), p.velocity().y(), 0 );
		if ( p.position().y() < 0 || p.position().y() > pg.height )
			p.velocity().set( p.velocity().x(), (float)-0.9*p.velocity().y(), 0 );
		p.position().set( PApplet.constrain( p.position().x(), 0, pg.width ), PApplet.constrain( p.position().y(), 0, pg.height ), 0 ); 
	}

	public void fillChars(){		
		for(int x = 0; x < numberOfColumns; x++){
			for(int y = 0; y < ahTextContext.column[x].y; y++){
				characters[y][x]  = (char)((int)ahTextContext.random(65, 88));
			}
		}
	}

	public void resetParticles() {
		physics.clear();
		for(int y = 0; y < numberOfRows; y++){
			for(int x = 0; x < numberOfColumns; x++){
				pg.fill(0);
				characters[y][x]  = (char)((int)ahTextContext.random(65, 88));
				particles[y][x] = physics.makeParticle( (float)1.0, (float)(x*boundWidth), y*boundHeight, 0 );
				if(x > 0){
					physics.makeAttraction( particles[y][x-1], particles[y][x], 10000 * ahTextContext.random(1), 10 );
				}
			}
		}
	}

}
