package ecs.systems;

import static org.junit.Assert.assertEquals;

import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import graphic.Animation;
import level.elements.ILevel;
import level.elements.tile.Tile;
import mydungeon.ECS;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tools.Point;

public class VelocitySystemTest {

    private VelocitySystem system;
    private final ILevel level = Mockito.mock(ILevel.class);
    private final Animation moveRight = Mockito.mock(Animation.class);
    private final Animation moveLeft = Mockito.mock(Animation.class);

    private final Animation idleRight = Mockito.mock(Animation.class);
    private final Animation idleLeft = Mockito.mock(Animation.class);
    private final Tile tile = Mockito.mock(Tile.class);
    private Entity entity;

    @Before
    public void setup() {
        ECS.systems = Mockito.mock(SystemController.class);
        system = new VelocitySystem();
        entity = new Entity();
        entity.addComponent(PositionComponent.name, new PositionComponent(entity, new Point(1, 2)));
        entity.addComponent(
                VelocityComponent.name,
                new VelocityComponent(entity, 1, 2, 1, 2, moveLeft, moveRight));
        entity.addComponent(
                AnimationComponent.name, new AnimationComponent(entity, idleLeft, idleRight));
        ECS.currentLevel = level;
        Mockito.when(level.getTileAt(Mockito.any())).thenReturn(tile);
    }

    @Test
    public void updateValidMove() {
        Mockito.when(tile.isAccessible()).thenReturn(true);
        system.update();
        PositionComponent positionComponent =
                (PositionComponent) entity.getComponent(PositionComponent.name);
        VelocityComponent velocityComponent =
                (VelocityComponent) entity.getComponent(VelocityComponent.name);
        Point position = positionComponent.getPosition();
        assertEquals(2, position.x, 0.001);
        assertEquals(4, position.y, 0.001);

        velocityComponent.setX(-4);
        velocityComponent.setY(-8);
        system.update();
        position = positionComponent.getPosition();
        assertEquals(-2, position.x, 0.001);
        assertEquals(-4, position.y, 0.001);
    }

    @Test
    public void updateUnValidMove() {
        Mockito.when(tile.isAccessible()).thenReturn(false);
        system.update();
        PositionComponent positionComponent =
                (PositionComponent) entity.getComponent(PositionComponent.name);
        Point position = positionComponent.getPosition();
        assertEquals(1, position.x, 0.001);
        assertEquals(2, position.y, 0.001);
    }

    @Test
    public void changeAnimation() {
        Mockito.when(tile.isAccessible()).thenReturn(true);
        VelocityComponent velocityComponent =
                (VelocityComponent) entity.getComponent(VelocityComponent.name);
        AnimationComponent animationComponent =
                (AnimationComponent) entity.getComponent(AnimationComponent.name);
        // right
        velocityComponent.setX(1);
        velocityComponent.setY(0);
        system.update();
        assertEquals(moveRight, animationComponent.getCurrentAnimation());

        // idleRight
        velocityComponent.setX(0);
        velocityComponent.setY(0);

        system.update();
        assertEquals(idleRight, animationComponent.getCurrentAnimation());

        // left
        velocityComponent.setX(-1);
        velocityComponent.setY(0);
        system.update();
        assertEquals(moveLeft, animationComponent.getCurrentAnimation());

        // idleLeft
        velocityComponent.setX(0);
        velocityComponent.setY(0);

        system.update();
        assertEquals(idleLeft, animationComponent.getCurrentAnimation());
        ;
    }
}
