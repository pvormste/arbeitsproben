using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace PlaneGame
{
	public class Base2D : DrawableGameComponent
	{
		// Game Reference
		protected PlaneGame plGame;

		// Position
		public Vector2 Position { get; set; }
		public Color DrawColor { get; set; }

		// Sprite
		public Texture2D Sprite { get; set; }

		public Base2D (PlaneGame game, float x, float y) : base(game)
		{
			plGame = game;
			Position = new Vector2(x, y);
			DrawColor = Color.White;
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

			plGame.SpriteBatch.Draw(Sprite, Position, DrawColor);
		}
	}
}

