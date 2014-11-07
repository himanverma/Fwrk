<div class="vendorReviews view">
<h2><?php echo __('Vendor Review'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($vendorReview['VendorReview']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Customer'); ?></dt>
		<dd>
			<?php echo $this->Html->link($vendorReview['Customer']['name'], array('controller' => 'customers', 'action' => 'view', $vendorReview['Customer']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor'); ?></dt>
		<dd>
			<?php echo $this->Html->link($vendorReview['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $vendorReview['Vendor']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Review'); ?></dt>
		<dd>
			<?php echo h($vendorReview['VendorReview']['review']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Ratings'); ?></dt>
		<dd>
			<?php echo h($vendorReview['VendorReview']['ratings']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Vendor Review'), array('action' => 'edit', $vendorReview['VendorReview']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Vendor Review'), array('action' => 'delete', $vendorReview['VendorReview']['id']), array(), __('Are you sure you want to delete # %s?', $vendorReview['VendorReview']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Reviews'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Review'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Customers'), array('controller' => 'customers', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Customer'), array('controller' => 'customers', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
	</ul>
</div>
