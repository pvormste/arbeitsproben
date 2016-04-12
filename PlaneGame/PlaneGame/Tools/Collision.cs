using System;
using Microsoft.Xna.Framework;

namespace PlaneGame
{
	public class Collision
	{

		/// <summary>
		/// Checks for Collision between two Planes
		/// </summary>
		public static bool BetweenPlanes(BasePlane plane, BasePlane other)
		{
			for (int i = 0; i < plane.Hitbox.Length; ++i)
			{

				// Absolute Positions of BOTTOM_RIGHT and TOP_LEFT of PLANE
				Vector2 planeAbsoluteBottomRight = new Vector2(plane.Hitbox[i].Rectangle.Right, plane.Hitbox[i].Rectangle.Bottom) + plane.Position;
				Vector2 planeAbsoluteTopLeft = new Vector2(plane.Hitbox[i].Rectangle.Left, plane.Hitbox[i].Rectangle.Top) + plane.Position;

				for(int j = 0; j < other.Hitbox.Length; ++j)
				{

					// Absolute Positions of TOP_LEFT and BOTTOM_RIGHT of OTHER
					Vector2 otherAbsoluteTopLeft = new Vector2(other.Hitbox[j].Rectangle.Left, other.Hitbox[j].Rectangle.Top) + other.Position;
					Vector2 otherAbsoluteBottomRight = new Vector2(other.Hitbox[j].Rectangle.Right, other.Hitbox[j].Rectangle.Bottom) + other.Position;

					if(collideRects(planeAbsoluteBottomRight, planeAbsoluteTopLeft, otherAbsoluteTopLeft, otherAbsoluteBottomRight))
						return true;
				}
			}

			return false;
		}

		/// <summary>
		/// Checks Collision between two Rects
		/// </summary>
		private static bool collideRects(Vector2 r1BottomRight, Vector2 r1TopLeft, Vector2 r2TopLeft, Vector2 r2BottomRight)
		{
			if(r1BottomRight.X > r2TopLeft.X && r1BottomRight.Y > r2TopLeft.Y
				&& r1TopLeft.X < r2BottomRight.X && r1TopLeft.Y < r2BottomRight.Y)
			{
				return true;
			}

			return false;
		}
	}
}

