package myveryOwnproject.src.airplane;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.LinkedList;

public class Airport extends Thread {

	private Cell cell;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image plane;
	private AirSpace asp;
	private int cellNumber;
	int rowNumber;
	int colunmNumber;
	// alterado
	private LinkedList<Plane> waitingPlanes = new LinkedList<Plane>();
	private boolean clicked = false;
	private Image newImage;
	private boolean vazio = false;

	// ver.6 alterado
	public boolean isVazio() {
		return vazio;
	}

	public Airport(AirSpace asp, Cell cell) {
		plane = toolkit.createImage("Airport.png");
		this.asp = asp;
		this.cell = cell;
		newImage = toolkit.createImage("");
	}

	public Airport(AirSpace asp, int cellNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.cellNumber = cellNumber;
		newImage = toolkit.createImage("");
	}

	public Airport(AirSpace asp, int rowNumber, int colunmNumber) {
		plane = toolkit.createImage("plane.png");
		this.asp = asp;
		this.rowNumber = rowNumber;
		this.colunmNumber = colunmNumber;
		newImage = toolkit.createImage("");
	}

	public synchronized void addPlane(Plane plane) {
		System.out.println("adicionou " + plane);

		try {
			// não é preciso para o isEmpty pk precisa de percorrer todos os
			// avioes e não especificar cada 1
			plane.setOnAir(false);
			plane.setImage(newImage);
			waitingPlanes.add(plane);
			// ver.6 alterado
			System.out.print("Adicionou ");
			for (Plane airplanes : waitingPlanes) {
				System.out.print(airplanes + ", ");
			}
			System.out.println();
			
			wait();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		notifyAll();
		
		vazio = false;
	}

	public boolean isDead(int i) {
		// ver.6 alterado
		for (Plane airplanes : waitingPlanes) {
			if (airplanes.getPlaneCell().getCelula() == i) {
				return true;
			}
		}
		return false;
	}

	// synchronized e liberta thread
	// destino aletorio? apos 1 tempo
	public boolean removePlanePositionList(int plane) {
		if (waitingPlanes.isEmpty())
			waitingPlanes.remove(plane);
		return true;
	}

	@Override
	public void run() {
		while (true) {
			asp.getRwl().repaint();
		}
	}

	public Cell getAirportCell() {
		return cell;
	}

	public void setPositionByCellNumber(int newCellNumber) {
		this.cellNumber = newCellNumber;
	}

	public Image getImage() {
		return plane;
	}

	public boolean airportIsSelected(Point point) {
		int col = (int) (point.getX() / (asp.getRwl().getWidth() / asp
				.getNumColumns()));// divide coord x pla largura cada celula->
		// indica qual a coluna onde estou
		int lin = (int) (point.getY() / (asp.getRwl().getHeight() / asp
				.getNumRows()));// ->indica qual a linha onde estou a contar
		// cima, =0
		int selected = col + lin * asp.getNumColumns();
		System.out.println("AIRPORT SELECTED ->" + " selected : " + selected
				+ " cell.getcelula :" + cell.getCelula());
		if (cell.getCelula() == selected) {

			clicked = true;
			return true;

		}
		clicked = false;
		return false;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setNotClicked() {
		clicked = false;

	}
}
