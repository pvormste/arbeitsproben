using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace PlaneGame
{
	public abstract class BasePlane : Base2D
	{
		// Health
		public int Health { get; set; }
		public int Speed { get; set; }

		// Hitboxes
		public Hitbox[] Hitbox { get; set; }

		public BasePlane (PlaneGame game, float x, float y) : base(game, x, y)
		{

		}

		public override void Initialize ()
		{
			base.Initialize ();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);
		}

		public override void Draw (GameTime gameTime)
		{
			base.Draw (gameTime);

			#if DEBUG
			// Draw the Hitboxes
			for(int i = 0; i < Hitbox.Length; ++i)
				Hitbox[i].Draw(gameTime);
			#endif
		}
	}
}

