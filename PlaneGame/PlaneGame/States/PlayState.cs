using System;
using Microsoft.Xna.Framework;

namespace PlaneGame
{
	public class PlayState : BaseState
	{
		public PlayState (PlaneGame game) : base(game)
		{

		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);
		}

		public override void Draw(GameTime gameTime)
		{
			Game.GraphicsDevice.Clear(Color.CornflowerBlue);

			plGame.SpriteBatch.Begin();
			base.Draw(gameTime);
			plGame.SpriteBatch.End();
		}
	}
}

