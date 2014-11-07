<div class="vendorDays view">
<h2><?php echo __('Vendor Day'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($vendorDay['VendorDay']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor'); ?></dt>
		<dd>
			<?php echo $this->Html->link($vendorDay['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $vendorDay['Vendor']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Day Name'); ?></dt>
		<dd>
			<?php echo h($vendorDay['VendorDay']['day_name']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Status'); ?></dt>
		<dd>
			<?php echo h($vendorDay['VendorDay']['status']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Vendor Day'), array('action' => 'edit', $vendorDay['VendorDay']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Vendor Day'), array('action' => 'delete', $vendorDay['VendorDay']['id']), array(), __('Are you sure you want to delete # %s?', $vendorDay['VendorDay']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('controller' => 'meal_menus', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Orders'), array('controller' => 'orders', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Order'), array('controller' => 'orders', 'action' => 'add')); ?> </li>
	</ul>
</div>
<div class="related">
	<h3><?php echo __('Related Meal Menus'); ?></h3>
	<?php if (!empty($vendorDay['MealMenu'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Day Id'); ?></th>
		<th><?php echo __('Recipe Id'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendorDay['MealMenu'] as $mealMenu): ?>
		<tr>
			<td><?php echo $mealMenu['id']; ?></td>
			<td><?php echo $mealMenu['vendor_day_id']; ?></td>
			<td><?php echo $mealMenu['recipe_id']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'meal_menus', 'action' => 'view', $mealMenu['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'meal_menus', 'action' => 'edit', $mealMenu['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'meal_menus', 'action' => 'delete', $mealMenu['id']), array(), __('Are you sure you want to delete # %s?', $mealMenu['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Orders'); ?></h3>
	<?php if (!empty($vendorDay['Order'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Vendor Day Id'); ?></th>
		<th><?php echo __('Customer Id'); ?></th>
		<th><?php echo __('Recipe Names'); ?></th>
		<th><?php echo __('Status'); ?></th>
		<th><?php echo __('Ordered At'); ?></th>
		<th><?php echo __('Ready At'); ?></th>
		<th><?php echo __('Delivered At'); ?></th>
		<th><?php echo __('Lat'); ?></th>
		<th><?php echo __('Long'); ?></th>
		<th><?php echo __('Status Reason'); ?></th>
		<th><?php echo __('Paid Via'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($vendorDay['Order'] as $order): ?>
		<tr>
			<td><?php echo $order['id']; ?></td>
			<td><?php echo $order['vendor_day_id']; ?></td>
			<td><?php echo $order['customer_id']; ?></td>
			<td><?php echo $order['recipe_names']; ?></td>
			<td><?php echo $order['status']; ?></td>
			<td><?php echo $order['ordered_at']; ?></td>
			<td><?php echo $order['ready_at']; ?></td>
			<td><?php echo $order['delivered_at']; ?></td>
			<td><?php echo $order['lat']; ?></td>
			<td><?php echo $order['long']; ?></td>
			<td><?php echo $order['status_reason']; ?></td>
			<td><?php echo $order['paid_via']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'orders', 'action' => 'view', $order['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'orders', 'action' => 'edit', $order['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'orders', 'action' => 'delete', $order['id']), array(), __('Are you sure you want to delete # %s?', $order['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Order'), array('controller' => 'orders', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
