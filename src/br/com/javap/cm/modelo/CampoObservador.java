package br.com.javap.cm.modelo;

@FunctionalInterface
public interface CampoObservador {
	public void eventHappened (Campo campo, CampoEvento evento);
}
