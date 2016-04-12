using System;
using System.Collections.Generic;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace PlaneGame
{
	public abstract class BaseState : DrawableGameComponent
	{
		// Unique ID
		protected int ID;

		// GameReference
		protected PlaneGame plGame;

		// List of Components
		protected List<GameComponent> components = new List<GameComponent>();
		public List<GameComponent> Components { get { return components; } }

		public BaseState (PlaneGame game) : base(game)
		{
			// Save Game Reference
			this.plGame = game;

			// Create Unique ID
			Random rand = new Random();
			ID = rand.Next();
		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update (GameTime gameTime)
		{
			foreach(GameComponent component in components)
			{
				if(component.Enabled)
					component.Update(gameTime);
			}
		}

		public override void Draw(GameTime gameTime)
		{
			foreach(GameComponent component in components)
			{
				if(component is DrawableGameComponent)
				{
					DrawableGameComponent drawComponent = (DrawableGameComponent)component;

					if(drawComponent.Visible)
						drawComponent.Draw(gameTime);
				}
			}
		}

		public void StateChange(object sender, EventArgs e)
		{

		}
	}
}

