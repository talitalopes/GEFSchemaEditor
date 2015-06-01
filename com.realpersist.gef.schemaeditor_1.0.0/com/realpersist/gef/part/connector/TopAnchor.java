/*
 * Created on Jul 13, 2004
 */
package com.realpersist.gef.part.connector;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Bottom anchor for joining connections between figures
 * 
 * @author Phil Zoio
 */
public class TopAnchor extends AbstractConnectionAnchor
{

	private int offset;

	public TopAnchor(IFigure source)
	{
		super(source);
	}

	public Point getLocation(Point reference)
	{
		Rectangle r = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(r);
		int off = r.width / 2;
		if (r.contains(reference) || r.y < reference.y)
			return r.getBottomLeft().translate(off, -1);
		else
			return r.getTopLeft().translate(off, 0);
	}

}