<div class="orders view">
<h2><?php echo __('Order'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($order['Order']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor Day'); ?></dt>
		<dd>
			<?php echo $this->Html->link($order['VendorDay']['id'], array('controller' => 'vendor_days', 'action' => 'view', $order['VendorDay']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Customer'); ?></dt>
		<dd>
			<?php echo $this->Html->link($order['Customer']['name'], array('controller' => 'customers', 'action' => 'view', $order['Customer']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Recipe Names'); ?></dt>
		<dd>
			<?php echo h($order['Order']['recipe_names']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Status'); ?></dt>
		<dd>
			<?php echo h($order['Order']['status']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Ordered At'); ?></dt>
		<dd>
			<?php echo h($order['Order']['ordered_at']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Ready At'); ?></dt>
		<dd>
			<?php echo h($order['Order']['ready_at']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Delivered At'); ?></dt>
		<dd>
			<?php echo h($order['Order']['delivered_at']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Lat'); ?></dt>
		<dd>
			<?php echo h($order['Order']['lat']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Long'); ?></dt>
		<dd>
			<?php echo h($order['Order']['long']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Status Reason'); ?></dt>
		<dd>
			<?php echo h($order['Order']['status_reason']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Paid Via'); ?></dt>
		<dd>
			<?php echo h($order['Order']['paid_via']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Order'), array('action' => 'edit', $order['Order']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Order'), array('action' => 'delete', $order['Order']['id']), array(), __('Are you sure you want to delete # %s?', $order['Order']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Orders'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Order'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('controller' => 'vendor_days', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Customers'), array('controller' => 'customers', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Customer'), array('controller' => 'customers', 'action' => 'add')); ?> </li>
	</ul>
</div>
