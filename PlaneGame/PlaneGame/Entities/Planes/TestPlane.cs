using System;
using Microsoft.Xna.Framework;

namespace PlaneGame
{
	public class TestPlane : BasePlane
	{
		public TestPlane (PlaneGame game, float x, float y) : base(game, x, y)
		{
			// Values
			Health = 100;
			Speed = 170;

			// Sprite
			Sprite = Assets.ImgTestplane;

			// Hitboxes
			Hitbox = new Hitbox[2];
			Hitbox[0] = new Hitbox(plGame, this, 8, Sprite.Height, 28, 0);
			Hitbox[1] = new Hitbox(plGame, this, Sprite.Width - 4, 13, 2, 20);
		}
	}
}

