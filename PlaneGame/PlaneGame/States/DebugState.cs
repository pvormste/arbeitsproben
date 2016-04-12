using System;
using Microsoft.Xna.Framework;

namespace PlaneGame
{
	public class DebugState : BaseState
	{
		// Player
		Player _player;

		// DebugPlane
		TestPlane _debugPlane;

		public DebugState (PlaneGame game) : base(game)
		{
			_player = new Player(plGame, new TestPlane(plGame, Game.GraphicsDevice.Viewport.Width / 2 - 32, Game.GraphicsDevice.Viewport.Height - 80));
			components.Add(_player);

			_debugPlane = new TestPlane(plGame, Game.GraphicsDevice.Viewport.Width / 2 - 32, Game.GraphicsDevice.Viewport.Height / 2 - 32);
			components.Add(_debugPlane);
		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);

			#if DEBUG
			Console.WriteLine("Collision: "+ Collision.BetweenPlanes(_player.Plane, _debugPlane));
			#endif
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

